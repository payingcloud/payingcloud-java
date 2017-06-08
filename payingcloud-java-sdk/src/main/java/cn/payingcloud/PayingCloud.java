package cn.payingcloud;

import cn.payingcloud.model.PcRequest;
import cn.payingcloud.model.PcResponse;
import cn.payingcloud.net.PcHttpClient;
import cn.payingcloud.net.PcHttpRequest;
import cn.payingcloud.net.PcHttpResponse;

/**
 * @author YQ.Huang
 */
public class PayingCloud {

    public static final String DEFAULT_ENDPOINT = "https://api.payingcloud.cn";
    public static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPymtLbRkHgvVfUT933LrwWns6YZHLPpT1pP9TKJ+cgIZiQwZ4mtqoqPHSVtiT5HA8fwFzWuJ/6qWaQhER7TOISUFUHZlHyBjNK/Z5px6PNB7rT4OrLP0KuZ7nuX5qdnOKuAbrj1MBLSinOHQ8tDJhPrPKxuZlKw3SeL5auHlKWwIDAQAB";

    private final String endpoint;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final PcHttpClient client;

    public PayingCloud(String accessKeyId, String accessKeySecret) {
        this(accessKeyId, accessKeySecret, DEFAULT_ENDPOINT);
    }

    public PayingCloud(String accessKeyId, String accessKeySecret, String endpoint) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.client = new PcHttpClient();
    }

    public <T extends PcResponse> T execute(PcRequest<T> request) {
        PcHttpRequest httpRequest = request.buildHttpRequest(accessKeyId, accessKeySecret, endpoint);
        PcHttpResponse httpResponse = client.execute(httpRequest);
        return httpResponse.parse(request.getResponseClass());
    }

}
