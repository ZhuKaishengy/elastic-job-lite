package io.elasticjob.elasticjobspringbootexample.exception;

import io.elasticjob.elasticjobspringbootexample.resp.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author zhukaishengy
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截捕捉参数异常 ParamValidateException.class
     */
    @ResponseBody
    @ExceptionHandler(value = ParamValidateException.class)
    public ServiceResult errorHandler(ParamValidateException paramValidateException) {
        log.error("入参检查错误 code={},message={}",paramValidateException.getErrorCode(),paramValidateException.getMessage());
        return ServiceResult.fail().setMsg(paramValidateException.getMessage()).setCode(paramValidateException.getErrorCode());
    }

    /**
     * 拦截捕捉通用异常
     */
    @ResponseBody
    @ExceptionHandler(value = BizException.class)
    public ServiceResult errorHandler(BizException bizException) {
        log.error("业务异常 code={},message={}",bizException.getErrorCode(), bizException.getMessage());
        return ServiceResult.fail().setMsg(bizException.getMessage()).setCode(bizException.getErrorCode());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ServiceResult errorHandler(Exception e) {
        log.error("系统异常 错误信息", e);
        return ServiceResult.fail();
    }
}
