package cn.payingcloud.model;

/**
 * @author YQ.Huang
 */
public enum PcChargeStatus {
    CREATED,        // 创建了
    STARTED,        // 开始了，等待买家付款
    SUCCEEDED,      // 成功了
    FAILED,         // 失败了
}
