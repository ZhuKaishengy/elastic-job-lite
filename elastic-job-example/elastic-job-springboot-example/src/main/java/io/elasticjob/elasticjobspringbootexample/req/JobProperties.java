package io.elasticjob.elasticjobspringbootexample.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/18 12:47
 * @Description:
 */
@Data
public class JobProperties {
    /**
     * 自定义异常处理类
     * @return
     */
    @JsonProperty("job_exception_handler")
    private String jobExceptionHandler = "io.elasticjob.lite.executor.handler.impl.DefaultJobExceptionHandler";
//    private String jobExceptionHandler = "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler";

    /**
     * 自定义业务处理线程池
     * @return
     */
    @JsonProperty("executor_service_handler")
    private String executorServiceHandler = "io.elasticjob.lite.executor.handler.impl.DefaultExecutorServiceHandler";
//    private String executorServiceHandler = "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler";
}
