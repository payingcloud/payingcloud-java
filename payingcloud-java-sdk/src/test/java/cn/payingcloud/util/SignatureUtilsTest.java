package cn.payingcloud.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author YQ.Huang
 */
public class SignatureUtilsTest {

    @Test
    public void sign() throws Exception {
        String secret = "12345678";
        String verb = "POST";
        String resource = "/charges";
        String body = "{\"amount\":1,\"metadata\":\"元数据\",\"subject\":\"测试商品-67832066\",\"extra\":{\"returnUrl\":\"http://localhost:8080/order/charge/return\"},\"channel\":\"ALIPAY_APP\",\"notifyUrl\":\"http://localhost:8080/order/charge/success\",\"remark\":\"备注\",\"chargeNo\":\"1495636612246\"}";
        String timestamp = "Wed, 24 May 2017 14:37:33 GMT";

        String sign = SignatureUtils.sign(secret, verb, resource, body, timestamp);

        assertEquals("0ab5d28d8c08c55342a553b5aeadf216085606b7", sign);
    }

    @Test
    public void generateAuthorizationHeader() throws Exception {
        String accessKeyId = "59257ff2e45b2900015e3b61";
        String sign = "0ab5d28d8c08c55342a553b5aeadf216085606b7";

        String header = "Basic " + Base64.encodeBase64String((accessKeyId + ":" + sign).getBytes());

        assertEquals("Basic NTkyNTdmZjJlNDViMjkwMDAxNWUzYjYxOjBhYjVkMjhkOGMwOGM1NTM0MmE1NTNiNWFlYWRmMjE2MDg1NjA2Yjc=", header);

    }

    @Test
    public void name() throws Exception {
        // 密钥
        String accessKeyId = "59257ff2e45b2900015e3b61";
        String accessKeySecret = "12345678";

        // 原始请求参数
        String endpoint = "https://api.payingcloud.cn";
        String verb = "POST";
        String resource = "/charges";
        String body = "{\"amount\":1,\"metadata\":\"元数据\",\"subject\":\"测试商品\",\"extra\":{\"returnUrl\":\"http://localhost:8080/order/charge/return\"},\"channel\":\"CMBCPAY_T0_WX_QR\",\"notifyUrl\":\"http://localhost:8080/order/charge/success\",\"remark\":\"test\",\"chargeNo\":\"1495636612246\"}";
        String timestamp = "Wed, 24 May 2017 14:37:33 GMT";

        // 签名
        String sign = SignatureUtils.sign(accessKeySecret, verb, resource, body, timestamp);
        // 生成Basic Authorization头
        String header = "Basic " + Base64.encodeBase64String((accessKeyId + ":" + sign).getBytes());

        // 设置请求Url
        String uri = endpoint + resource;
        RequestBuilder requestBuilder = RequestBuilder.create(verb).setUri(uri);
        // 设置请求头类型
        StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
        requestBuilder.setEntity(entity);
        // 设置请求头
        requestBuilder.addHeader("Date", timestamp);
        requestBuilder.addHeader("Authorization", header);
        // 执行请求
        HttpUriRequest request = requestBuilder.build();
        HttpClient httpClient = HttpClients.custom().build();
        HttpResponse response = httpClient.execute(request);
        // 返回结果状态码是400（因为未配置渠道）
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}