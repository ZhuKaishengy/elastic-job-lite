package io.elasticjob.elasticjobspringbootexample.exception;

/**
 * 业务异常枚举
 *
 * @author zhukaishengy
 */
public enum BizExceptionEnum {
    /**
     * 定义异常枚举
     */
    SYS_EXCEPTION(1, "系统异常"),
    PARAMETER_DEFECT(4,"缺少必要参数:"),
    PARAM_EXCEPTION(100, "参数错误"),
    BIZ_EXCEPTION(2000, "业务异常"),
    LACK_SCRIPTCOMMANDLINE(2001, "缺少命令脚本");



    private final Integer errorCode;
    private final String errordesc;

    BizExceptionEnum(Integer errorCode, String errordesc) {
        this.errorCode = errorCode;
        this.errordesc = errordesc;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrordesc() {
        return errordesc;
    }
}