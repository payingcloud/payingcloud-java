package cn.payingcloud.net;

/**
 * 客户端异常，例如IOException，Json解析异常等
 *
 * @author YQ.Huang
 */
public class PcClientException extends RuntimeException {

    public PcClientException(Throwable cause) {
        super(cause);
    }
}
