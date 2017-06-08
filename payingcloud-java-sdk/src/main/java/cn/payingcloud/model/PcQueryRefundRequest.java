package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author YQ.Huang
 */
public class PcQueryRefundRequest implements PcRequest<PcRefund> {

    private final String refundNo;

    public PcQueryRefundRequest(String refundNo) {
        this.refundNo = refundNo;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/charges/" + refundNo)
                .withMethod("GET");
    }

    @Override
    public Class<PcRefund> getResponseClass() {
        return PcRefund.class;
    }
}
