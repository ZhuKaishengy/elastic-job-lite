package io.elasticjob.elasticjobspringbootexample.job.template;

import io.elasticjob.elasticjobspringbootexample.exception.BizException;
import io.elasticjob.elasticjobspringbootexample.exception.BizExceptionEnum;
import io.elasticjob.elasticjobspringbootexample.job.BaseSimpleJob;
import io.elasticjob.lite.api.ShardingContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/31 14:35
 * @Description: job执行用于回调url
 */
public class UrlSimpleJob extends BaseSimpleJob {

    private static final String PARAM_SEPARATOR = ",";

    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        RestTemplate restTemplate = applicationContext.getBean(RestTemplate.class);
        String jobParameter = shardingContext.getJobParameter();
        String[] split = jobParameter.split(PARAM_SEPARATOR);
        if (split.length != 2) {
            throw new BizException(BizExceptionEnum.PARAM_EXCEPTION);
        }
        String requestType = split[0];
        String url = split[1];
        if (requestType.equalsIgnoreCase(HttpMethod.GET.name())) {
            restTemplate.getForObject(url, Object.class);
        }
    }

}
