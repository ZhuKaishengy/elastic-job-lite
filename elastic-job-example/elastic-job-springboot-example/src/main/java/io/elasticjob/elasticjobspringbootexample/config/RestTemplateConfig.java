package io.elasticjob.elasticjobspringbootexample.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/31 15:56
 * @Description:
 */
@Configuration
@ConditionalOnMissingBean(RestTemplate.class)
public class RestTemplateConfig {

    // TODO 在springcloud里用ribbon实现负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
