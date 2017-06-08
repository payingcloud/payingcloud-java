package cn.payingcloud.model;

import cn.payingcloud.net.PcHttpRequest;

/**
 * @author YQ.Huang
 */
public class PcQueryChargeRequest implements PcRequest<PcCharge> {

    private final String chargeNo;

    public PcQueryChargeRequest(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    @Override
    public PcHttpRequest buildHttpRequest(String accessKeyId, String accessKeySecret, String endpoint) {
        return new PcHttpRequest(accessKeyId, accessKeySecret, endpoint, "/charges/" + chargeNo)
                .withMethod("GET");
    }

    @Override
    public Class<PcCharge> getResponseClass() {
        return PcCharge.class;
    }
}
