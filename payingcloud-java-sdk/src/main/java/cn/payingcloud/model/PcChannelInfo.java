package cn.payingcloud.model;


/**
 * @author ZM.Wang
 */
public class PcChannelInfo {
    private PcChannelType channel;
    private String name;
    private String imageUrl;

    public PcChannelType getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setChannel(PcChannelType channel) {
        this.channel = channel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
