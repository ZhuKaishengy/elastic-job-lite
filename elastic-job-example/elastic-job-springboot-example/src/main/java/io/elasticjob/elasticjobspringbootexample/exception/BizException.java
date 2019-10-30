package io.elasticjob.elasticjobspringbootexample.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 * @author zhukaishengy
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public BizException(Integer errorCode, String errorMsg, Throwable throwable) {
        super(errorMsg, throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(BizExceptionEnum bizExceptionEnum){
        super(bizExceptionEnum.getErrordesc());
        this.errorCode = bizExceptionEnum.getErrorCode();
        this.errorMsg = bizExceptionEnum.getErrordesc();
    }
}
