package cn.payingcloud.model;

import java.util.Date;
import java.util.Map;

/**
 * @author YQ.Huang
 */
public class PcCharge implements PcObject {
    private String appId;
    private String chargeNo;
    private String subject;
    private String remark;
    private PcChannelType channel;
    private int amount;
    private int refundedAmount;
    private String metadata;
    private Map<String, String> credentials;
    private PcChargeStatus status;
    private String errorMessage;
    private Date startedAt;
    private Date completedAt;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PcChannelType getChannel() {
        return channel;
    }

    public void setChannel(PcChannelType channel) {
        this.channel = channel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public int getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(int refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }

    public void setCredentials(Map<String, String> credentials) {
        this.credentials = credentials;
    }

    public PcChargeStatus getStatus() {
        return status;
    }

    public void setStatus(PcChargeStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

}
