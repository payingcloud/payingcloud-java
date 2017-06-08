package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YQ.Huang
 */
public class PcChargeRequest implements PcRequest<PcCharge> {

    private final String chargeNo;
    private final String subject;
    private String remark;
    private final int amount;
    private final PcChannelType channel;
    private String metadata;
    private Map<String, String> extra;
    private String notifyUrl;

    public PcChargeRequest(String chargeNo, String subject, int amount, PcChannelType channel) {
        this.chargeNo = chargeNo;
        this.subject = subject;
        this.amount = amount;
        this.channel = channel;
    }

    public String getChargeNo() {
        return chargeNo;
    }

    public String getSubject() {
        return subject;
    }

    public int getAmount() {
        return amount;
    }

    public PcChannelType getChannel() {
        return channel;
    }

    public String getRemark() {
        return remark;
    }

    public PcChargeRequest setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public PcChargeRequest setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
        return this;
    }

    public String getMetadata() {
        return metadata;
    }

    public PcChargeRequest setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public PcChargeRequest setExtra(Map<String, String> extra) {
        this.extra = extra;
        return this;
    }

    public PcChargeRequest withExtra(String key, String value) {
        if (extra == null) {
            extra = new HashMap<String, String>();
        }
        extra.put(key, value);
        return this;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/charges")
                .withMethod("POST")
                .withParam("chargeNo", chargeNo)
                .withParam("subject", subject)
                .withParam("amount", amount)
                .withParam("remark", remark)
                .withParam("metadata", metadata)
                .withParam("channel", channel)
                .withParam("extra", extra)
                .withParam("notifyUrl", notifyUrl);
    }

    @Override
    public Class<PcCharge> getResponseClass() {
        return PcCharge.class;
    }
}
