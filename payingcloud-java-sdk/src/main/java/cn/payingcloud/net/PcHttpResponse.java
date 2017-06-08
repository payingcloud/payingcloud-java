package cn.payingcloud.net;

import cn.payingcloud.model.PcError;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author YQ.Huang
 */
public class PcHttpResponse {
    private int statusCode;
    private String reasonPhrase;
    private String contentType;
    private InputStream body;

    PcHttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public <RESPONSE_TYPE> RESPONSE_TYPE parse(Class<RESPONSE_TYPE> responseClass) {
        try {
            if (statusCode >= 200 && statusCode < 300)
                return PcHttpClient.MAPPER.readValue(body, responseClass);
            else {
                PcError error = PcHttpClient.MAPPER.readValue(body, PcError.class);
                throw new PcServerException(error);
            }
        } catch (IOException e) {
            throw new PcClientException(e);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    PcHttpResponse setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    PcHttpResponse setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public InputStream getBody() {
        return body;
    }

    PcHttpResponse setBody(InputStream body) {
        this.body = body;
        return this;
    }

}
