package cn.payingcloud.model;

/**
 * @author YQ.Huang
 */
public enum PcChannelType {
    ALIPAY_APP,          //支付宝APP支付
    ALIPAY_DIRECT,       //支付宝即时到账
    ALIPAY_QR,           //支付宝扫描付
    ALIPAY_WAP,          //支付宝手机网页支付
    WXPAY_APP,           //微信APP支付
    WXPAY_JSAPI,         //微信公众号支付
    WXPAY_NATIVE,        //微信扫码支付
    UPACP_APP,           //银联手机控件支付
    UPACP_GATEWAY,       //银联网关支付
    JDPAY_WEB,           //京东钱包PC网页支付
    JDPAY_WAP,           //京东钱包移动网页支付
    JDPAY_QR,            //京东钱包扫码支付
    BDPAY_WEB,           //百度钱包PC网页支付
    BDPAY_WAP,           //百度钱包移动网页支付
    BDPAY_QR,            //百度钱包扫码支付
    BJPAY_WEB,           //首信易支付PC网页支付
    BJPAY_WX,            //首信易支付微信支付
    CHINAPAY_WEB,        //首信易支付微信支付
    YEEPAY_WEB,          //易宝PC网页支付
    YEEPAY_WAP,          //易宝移动网页支付
    KFTPAY_WX,           //快付通微信扫码支付
    KFTPAY_ALI,          //快付通支付宝扫码支付
    KFTPAY_CASHIER,      //快付通扫码收银台
    CMBCPAY_T0_ALI,      //民生银行T0支付宝扫码支付
    CMBCPAY_T1_ALI,      //民生银行T1支付宝扫码支付
    CMBCPAY_T0_WX_QR,    //民生银行T0微信扫码支付
    CMBCPAY_T1_WX_QR,    //民生银行T1微信扫码支付
    CMBCPAY_T0_WX_JSAPI, //民生银行T0微信公众号支付
    CMBCPAY_T1_WX_JSAPI, //民生银行T1微信公众号支付
    CMBCPAY_T0_QQ,       //民生银行T0QQ扫码支付
    CMBCPAY_T1_QQ,       //民生银行T1QQ扫码支付
    CMBCPAY_T1_JD,       //民生银行T1京东扫码支付
    CMBCPAY_T1_BD,       //民生银行T1百度扫码支付
    WEBANKPAY_WX_QR,     //微众银行微信扫码支付
    WEBANKPAY_WX_H5,     //微众银行微信H5支付
    WEBANKPAY_WX_JSAPI,  //微众银行微信公众号支付
}
