package cn.payingcloud.net;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * @author YQ.Huang
 */
public class PcHttpClient {
    public static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private HttpClient httpClient = HttpClients.custom().build();

    public PcHttpResponse execute(PcHttpRequest pcHttpRequest) {
        try {
            HttpUriRequest httpRequest = pcHttpRequest.build();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            return new PcHttpResponse(httpResponse.getStatusLine().getStatusCode())
                    .setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase())
                    .setBody(httpResponse.getEntity().getContent())
                    .setContentType(httpResponse.getEntity().getContentType().getValue());
        } catch (IOException e) {
            throw new PcClientException(e);
        }
    }

}
