package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author YQ.Huang
 */
public class PcQueryRefundListRequest implements PcRequest<PcRefundList> {

    private final String chargeNo;
    private final int page;
    private final int size;

    public PcQueryRefundListRequest(String chargeNo, int page, int size) {
        this.chargeNo = chargeNo;
        this.page = page;
        this.size = size;
    }

    public String getChargeNo() {
        return chargeNo;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/refunds")
                .withMethod("GET")
                .withParam("chargeNo", chargeNo)
                .withParam("page", page)
                .withParam("size", size);
    }

    @Override
    public Class<PcRefundList> getResponseClass() {
        return PcRefundList.class;
    }
}
