package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author YQ.Huang
 */
public class PcRefundRequest implements PcRequest<PcRefund> {

    private final String chargeNo;
    private final String refundNo;
    private final int amount;
    private String remark;
    private String metadata;
    private String notifyUrl;

    public PcRefundRequest(String chargeNo, String refundNo, int amount) {
        this.chargeNo = chargeNo;
        this.refundNo = refundNo;
        this.amount = amount;
    }

    public String getChargeNo() {
        return chargeNo;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public int getAmount() {
        return amount;
    }

    public String getRemark() {
        return remark;
    }

    public PcRefundRequest setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getMetadata() {
        return metadata;
    }

    public PcRefundRequest setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PcRefundRequest setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/refunds")
                .withMethod("POST")
                .withParam("chargeNo", chargeNo)
                .withParam("refundNo", refundNo)
                .withParam("amount", amount)
                .withParam("remark", remark)
                .withParam("metadata", metadata)
                .withParam("notifyUrl", notifyUrl);
    }

    @Override
    public Class<PcRefund> getResponseClass() {
        return PcRefund.class;
    }
}
