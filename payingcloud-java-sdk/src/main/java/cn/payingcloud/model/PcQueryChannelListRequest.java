package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author ZM.Wang
 */
public class PcQueryChannelListRequest implements PcRequest<PcChannelList> {

    private PcChannelCategory category = PcChannelCategory.ALL;

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/channels")
                .withMethod("GET")
                .withParam("category", category);
    }

    @Override
    public Class<PcChannelList> getResponseClass() {
        return PcChannelList.class;
    }

    public void setCategory(PcChannelCategory category) {
        this.category = category;
    }

    public PcChannelCategory getCategory() {
        return category;
    }
}
