# payingcloud-java

payingcloud-java是对[Payingcloud聚合支付API](https://payingcloud.github.io/payingcloud-api-doc/)的Java版本实现及其相关演示，包括两个子项目：
* payingcloud-java-sdk
  Java版本SDK开放源码
* payingcloud-java-demo
  Java版本SDK使用演示

## PayingCloud Java SDK 使用说明

1. API文档包含了各接口的技术规格说明，建议在实际使用该SDK进行开发之前先阅读[API文档](https://payingcloud.github.io/payingcloud-api-doc/)以理解相关概念
1. 引入依赖
    ```
    <dependency>
        <groupId>cn.payingcloud</groupId>
        <artifactId>payingcloud-java</artifactId>
        <version>1.0.5</version>
    </dependency>
    ```
1. 使用平台密钥构建PayingCloud对象
    ```java
    PayingCloud payingcloud = new PayingCloud("Your AccessKeyId", "Your AccessKeySecret");
    ```
1. 使用PayingCloud发起对接口的调用，并对调用结果做相应处理
    ```java
    PcChargeRequest chargeRequest = new PcChargeRequest("orderNo", "subject", amount, channel);
    PcCharge charge = payingcloud.execute(chargeRequest);
    ```