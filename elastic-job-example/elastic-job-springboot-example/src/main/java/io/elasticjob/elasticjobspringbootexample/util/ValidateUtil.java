package io.elasticjob.elasticjobspringbootexample.util;

import io.elasticjob.elasticjobspringbootexample.exception.ParamValidateException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/18 09:29
 * @Description: 入参校验类
 */
public class ValidateUtil {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new ParamValidateException(constraint.getPropertyPath() + constraint.getMessage());
        }
    }

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ParamValidateException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ParamValidateException(message);
        }
    }
}
