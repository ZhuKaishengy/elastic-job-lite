package io.elasticjob.elasticjobspringbootexample.exception;

/**
 * 参数校验异常
 *
 * @author zhukaishengy
 */
public class ParamValidateException extends BizException {
    public ParamValidateException(String errorMsg) {
        super(BizExceptionEnum.PARAMETER_DEFECT.getErrorCode(),BizExceptionEnum.PARAMETER_DEFECT.getErrordesc()+errorMsg);
    }
}
