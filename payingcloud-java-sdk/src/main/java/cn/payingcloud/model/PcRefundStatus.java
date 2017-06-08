package cn.payingcloud.model;

/**
 * @author YQ.Huang
 */
public enum PcRefundStatus {
    CREATED,        // 创建了
    STARTED,        // 开始了，正在处理中
    SUCCEEDED,      // 成功了
    FAILED,         // 失败了
}
