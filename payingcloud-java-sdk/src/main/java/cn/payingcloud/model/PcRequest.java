package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author YQ.Huang
 */
public interface PcRequest<RESPONSE_TYPE extends PcResponse> {

    PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint);

    Class<RESPONSE_TYPE> getResponseClass();
}
