package cn.payingcloud.model;

import java.util.List;

/**
 * @author ZM.Wang
 */
public class PcChannelList implements PcObject {

    private List<PcChannelInfo> content;
    private int numberOfElements;

    public List<PcChannelInfo> getContent() {
        return content;
    }

    public void setContent(List<PcChannelInfo> content) {
        this.content = content;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
