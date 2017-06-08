package cn.payingcloud.net;

import cn.payingcloud.util.SignatureUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YQ.Huang
 */
public class PcHttpRequest {
    private final Map<String, Object> params = new HashMap<String, Object>();
    private final Map<String, String> headers = new HashMap<String, String>();
    private final String endpoint;
    private final String urlPath;
    private final String accessKeyId;
    private final String accessKeySecret;
    private String method = "GET";

    public PcHttpRequest(String accessKeyId, String accessKeySecret, String endpoint, String urlPath) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.endpoint = endpoint;
        this.urlPath = urlPath;
    }

    public PcHttpRequest withMethod(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    public PcHttpRequest withHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public PcHttpRequest withParam(String key, Object value) {
        if (value != null) {
            params.put(key, value);
        }
        return this;
    }

    public HttpUriRequest build() throws JsonProcessingException {
        String uri = endpoint + urlPath;
        RequestBuilder requestBuilder = RequestBuilder.create(method).setUri(uri);
        String resource = urlPath;
        String body = "";
        if ("POST".equals(method) || "PUT".equals(method)) {
            body = PcHttpClient.MAPPER.writeValueAsString(params);
            StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
            requestBuilder.setEntity(entity);
        } else {
            for (String key : params.keySet()) {
                requestBuilder.addParameter(key, params.get(key).toString());
            }
            String query = SignatureUtils.buildQuery(params);
            if (query != null && !query.isEmpty()) {
                resource += "?" + query;
            }
        }
        String timestamp = DateUtils.formatDate(new Date());
        String signature = SignatureUtils.sign(accessKeySecret, method, resource, body, timestamp);
        withHeader("Date", timestamp);
        withHeader("Authorization", "Basic "
                + Base64.encodeBase64String((accessKeyId + ":" + signature).getBytes()));
        for (String headerKey : headers.keySet()) {
            requestBuilder.addHeader(headerKey, headers.get(headerKey));
        }
        return requestBuilder.build();
    }

}
