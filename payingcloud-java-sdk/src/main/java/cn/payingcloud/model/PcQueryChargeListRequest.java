package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

import java.util.Date;

/**
 * @author YQ.Huang
 */
public class PcQueryChargeListRequest implements PcRequest<PcChargeList> {

    private final Date from;
    private final Date to;
    private final int page;
    private final int size;

    public PcQueryChargeListRequest(Date from, Date to, int page, int size) {
        this.from = from;
        this.to = to;
        this.page = page;
        this.size = size;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/charges")
                .withMethod("GET")
                .withParam("from", from.getTime())
                .withParam("to", to.getTime())
                .withParam("page", page)
                .withParam("size", size);
    }

    @Override
    public Class<PcChargeList> getResponseClass() {
        return PcChargeList.class;
    }
}
