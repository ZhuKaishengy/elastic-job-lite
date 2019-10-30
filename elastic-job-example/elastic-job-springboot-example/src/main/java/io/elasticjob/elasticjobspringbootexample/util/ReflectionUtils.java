package io.elasticjob.elasticjobspringbootexample.util;

import io.elasticjob.elasticjobspringbootexample.exception.BizException;
import io.elasticjob.elasticjobspringbootexample.exception.BizExceptionEnum;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/29 20:24
 * @Description:
 */
public class ReflectionUtils {

    public static Object getInstance(String className) {
        try {
            Class<?> aClass = Class.forName(className);
            return aClass.newInstance();
        } catch (Exception e) {
            throw new BizException(BizExceptionEnum.BIZ_EXCEPTION);
        }
    }
}
