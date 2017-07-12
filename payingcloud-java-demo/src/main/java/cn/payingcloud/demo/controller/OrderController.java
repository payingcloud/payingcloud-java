package cn.payingcloud.demo.controller;

import cn.payingcloud.PayingCloud;
import cn.payingcloud.model.*;
import cn.payingcloud.util.SignatureUtils;
import cn.payingcloud.weixin.mp.basic.WxBasicApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author YQ.Huang
 */
@Api(tags = "order", description = " ")
@Controller
@RequestMapping("/order")
class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private final PayingCloud payingcloud;
    private final WxBasicApi wxBasicApi;

    @Autowired
    public OrderController(PayingCloud payingcloud, WxBasicApi wxBasicApi) {
        this.payingcloud = payingcloud;
        this.wxBasicApi = wxBasicApi;
    }

    @ApiOperation("收款")
    @GetMapping("/charge")
    public void charge(@RequestParam PcChannelType channel, HttpServletResponse servletResponse) throws Exception {
        switch (channel) {
            // 微信公众号支付需要先获取用户授权才可获取openId，因此单独处理
            case WXPAY_JSAPI:
            case CMBCPAY_T0_WX_JSAPI:
            case CMBCPAY_T1_WX_JSAPI:
            case WEBANKPAY_WX_JSAPI:
                String redirectUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/wx").build().toUriString();
                String url = wxBasicApi.getBaseAuthorizeUrl(redirectUrl, channel.toString());
                servletResponse.sendRedirect(url);
                return;
            // 快付通固定收银台直接展示即可
            case KFTPAY_CASHIER:
                servletResponse.sendRedirect("/pages/qrcode.html?codeUrl=https://jhpay.kftpay.com.cn/cloud/cloudplatform/payChannel/qrcodePay/000020010000000001.html");
                return;
        }
        // 其它渠道收款
        PcChargeRequest chargeRequest = buildChargeRequest(channel, buildChargeReturnUrl(), null);
        PcCharge charge = payingcloud.execute(chargeRequest);
        respondCharge(channel, charge, servletResponse);
    }

    @ApiOperation("微信公众号收款")
    @GetMapping("/charge/wx")
    public void WeixinCharge(@RequestParam String code, @RequestParam String state, HttpServletResponse servletResponse) throws Exception {
        // 微信渠道收款
        PcChannelType channel = PcChannelType.valueOf(state);
        String openId = wxBasicApi.getOauth2AccessToken(code).getOpenId();
        PcChargeRequest chargeRequest = buildChargeRequest(channel, buildChargeReturnUrl(), openId);
        PcCharge charge = payingcloud.execute(chargeRequest);
        respondCharge(channel, charge, servletResponse);
    }

    @RequestMapping("/charge/return")
    @ResponseBody
    public String onChargeReturn() {
        logger.info("跳转到支付成功页面");
        return "支付成功";
    }

    @ApiOperation("收款回调（通知）")
    @PostMapping("/charge/success")
    @ResponseBody
    public String onChargeSuccess(@RequestBody String body, @RequestHeader String sign) throws IOException, SignatureException {
        if (SignatureUtils.verify(body, sign)) {
            logger.info("验签通过");
        } else {
            logger.error("验签未通过");
            return "验签未通过";
        }
        PcCharge charge = mapper.readValue(body, PcCharge.class);
        String chargeNo = charge.getChargeNo();
        logger.info("收到单号[{}]的收款成功通知", chargeNo);
        return "success"; // 处理成功后应返回success，否则将重复接收
    }

    @ApiOperation("退款")
    @GetMapping("/refund")
    public void refund(@RequestParam String chargeNo, HttpServletResponse servletResponse) throws Exception {
        String refundNo = new Date().getTime() + "";
        int amount = 1;
        PcRefundRequest refundRequest = new PcRefundRequest(chargeNo, refundNo, amount);
        refundRequest.setNotifyUrl(buildRefundNotifyUrl());
        PcRefund refund = payingcloud.execute(refundRequest);
        if (refund.getChannel() == PcChannelType.ALIPAY_DIRECT) {
            servletResponse.getWriter().println(refund.getCredentials().get("url"));
        } else {
            servletResponse.getWriter().println("退款请求已发出");
        }
        servletResponse.getWriter().flush();
    }

    @ApiOperation("退款回调（通知）")
    @PostMapping("/refund/success")
    @ResponseBody
    public String onRefundSuccess(@RequestBody String body, @RequestHeader String sign) throws SignatureException, IOException {
        if (SignatureUtils.verify(body, sign)) {
            logger.info("验签通过");
        } else {
            logger.error("验签未通过");
            return "验签未通过";
        }
        PcRefund refund = mapper.readValue(body, PcRefund.class);
        String refundNo = refund.getRefundNo();
        logger.info("收到单号[{}]的退款成功通知", refundNo);
        return "success"; // 处理成功后应返回success，否则将重复接收
    }

    @ApiOperation("查询收款列表")
    @GetMapping("/list/charge")
    @ResponseBody
    public PcChargeList queryChargeList(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "20") int size) throws Exception {
        Date to = new Date();
        Date from = Date.from(to.toInstant().minus(30, ChronoUnit.DAYS));
        PcQueryChargeListRequest request = new PcQueryChargeListRequest(from, to, page, size);
        return payingcloud.execute(request);
    }

    @ApiOperation("查询退款列表")
    @GetMapping("/list/refund")
    @ResponseBody
    public PcRefundList queryRefundList(@RequestParam String chargeNo,
                                        @RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "20") int size) throws Exception {
        PcQueryRefundListRequest request = new PcQueryRefundListRequest(chargeNo, page, size);
        return payingcloud.execute(request);
    }

    private PcChargeRequest buildChargeRequest(PcChannelType channel, String returnUrl, String openId) throws IOException {
        String chargeNo = new Date().getTime() + "";
        String subject = "支付演示";
        String remark = "备注";
        int amount = 10;
        String metadata = "元数据";
        PcChargeRequest request = new PcChargeRequest(chargeNo, subject, amount, channel)
                .setRemark(remark)
                .setMetadata(metadata);
        if (returnUrl != null)
            request.withExtra("returnUrl", buildChargeReturnUrl());
        if (openId != null) {
            request.withExtra("openId", openId);
        }
        if (channel == PcChannelType.WEBANKPAY_WX_H5) {
            request.withExtra("mchCreateIp", "115.100.63.170");
            request.withExtra("sceneInfo", "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\":\"http://demo.payingcloud.cn\",\"wap_name\": \"PayingCloud Demo\"}}");
        }
        request.setNotifyUrl(buildChargeNotifyUrl());
        return request;
    }

    private String buildChargeReturnUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/order/charge/return")
                .replaceQuery(null)
                .build()
                .toUriString();
    }

    private String buildChargeNotifyUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/order/charge/success")
                .replaceQuery(null)
                .build()
                .toUriString();
    }

    private String buildRefundNotifyUrl() {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/order/refund/success")
                .replaceQuery(null)
                .build()
                .toUriString();
    }

    /**
     * 展示收银台
     */
    private void respondCharge(PcChannelType channel, PcCharge charge, HttpServletResponse servletResponse) throws IOException {
        switch (channel) {
            case BDPAY_WEB://已测通
            case BDPAY_WAP://已测通
                servletResponse.sendRedirect(charge.getCredentials().get("url"));
                break;
            case WEBANKPAY_WX_H5:
                servletResponse.sendRedirect(charge.getCredentials().get("mwebUrl"));
                break;
            case ALIPAY_APP://已测通
                servletResponse.getWriter().write(charge.getCredentials().get("requestString"));
                break;
            case ALIPAY_DIRECT: // 已测通
            case BJPAY_WEB://已测通
            case CHINAPAY_WEB://已测通
            case JDPAY_WEB://已测通
            case JDPAY_WAP://已测通
            case YEEPAY_WAP://已测通
            case ALIPAY_WAP: // 已测通
            case UPACP_GATEWAY: // 已测通
                servletResponse.setContentType("text/html;charset=UTF-8");
                servletResponse.getWriter().write(charge.getCredentials().get("html"));
                break;
            case YEEPAY_WEB://已测通
                servletResponse.setContentType("text/html;charset=gbk");
                servletResponse.getWriter().write(charge.getCredentials().get("html"));
                break;
            case BDPAY_QR://已测通
                servletResponse.sendRedirect("/pages/qrcode.html?codeImage=" + charge.getCredentials().get("url"));
                break;
            case WXPAY_APP://已测通
                servletResponse.getWriter().write(charge.getCredentials().toString());
                break;
            case WXPAY_NATIVE: // 已测通
            case BJPAY_WX: // 已测通
            case ALIPAY_QR: // 已测通
            case KFTPAY_WX://已测通
            case KFTPAY_ALI://已测通
            case JDPAY_QR://已测通
            case CMBCPAY_T1_ALI://已测通
            case WEBANKPAY_WX_QR://已测通
            case CMBCPAY_T0_ALI://已测通
            case CMBCPAY_T0_WX_QR://已测通
            case CMBCPAY_T1_WX_QR://已测通
            case CMBCPAY_T0_QQ://已测通
            case CMBCPAY_T1_QQ://已测通
            case CMBCPAY_T1_JD://已测通
            case CMBCPAY_T1_BD://已测通
                servletResponse.sendRedirect("/pages/qrcode.html?codeUrl=" + charge.getCredentials().get("codeUrl"));
                break;
            case WXPAY_JSAPI: // 已测通
            case CMBCPAY_T0_WX_JSAPI://已测通
            case CMBCPAY_T1_WX_JSAPI://已测通
            case WEBANKPAY_WX_JSAPI://已测通
                String params = "appId=" + charge.getCredentials().get("appId") + "&" +
                        "timeStamp=" + charge.getCredentials().get("timeStamp") + "&" +
                        "nonceStr=" + charge.getCredentials().get("nonceStr") + "&" +
                        "package=" + URLEncoder.encode(charge.getCredentials().get("package"), "UTF-8") + "&" +
                        "signType=" + charge.getCredentials().get("signType") + "&" +
                        "paySign=" + charge.getCredentials().get("paySign");
                servletResponse.sendRedirect("/weixin_jsapi_pay.html?" + params);
                break;
            case UPACP_APP: // 已测通
                servletResponse.getWriter().write(charge.getCredentials().get("tn"));
                break;
        }
    }

}
