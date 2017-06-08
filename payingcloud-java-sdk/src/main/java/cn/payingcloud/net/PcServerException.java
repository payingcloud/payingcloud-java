package cn.payingcloud.net;

import cn.payingcloud.model.PcError;

/**
 * 服务端响应异常，例如：PayingCloud响应400，500等错误
 *
 * @author YQ.Huang
 */
public class PcServerException extends RuntimeException {

    private PcError error;

    public PcServerException(PcError error) {
        super(error.getError() + ":" + error.getErrorDescription());
        this.error = error;
    }

    public PcError getError() {
        return error;
    }
}
