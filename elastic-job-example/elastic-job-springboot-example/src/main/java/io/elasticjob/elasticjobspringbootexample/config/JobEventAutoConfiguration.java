package io.elasticjob.elasticjobspringbootexample.config;

import io.elasticjob.lite.event.JobEventBus;
import io.elasticjob.lite.event.JobEventConfiguration;
import io.elasticjob.lite.event.rdb.JobEventRdbConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 16:20
 * @Description: 任务事件配置类
 */
@Configuration
public class JobEventAutoConfiguration {

    /**
     * 配置rdb数据源
     * @return 数据源
     */
    @Bean
    public DataSource eventTraceDataSource(@Value("${spring.datasource.url}") String url, @Value("${spring.datasource.driver-class-name}") String driverClass,
                                           @Value("${spring.datasource.username}") String username, @Value("${spring.datasource.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public JobEventConfiguration jobEventRdbConfiguration(DataSource dataSource) {
        return new JobEventRdbConfiguration(dataSource);
    }

    @Bean
    public JobEventBus jobEventBus(JobEventConfiguration jobEventConfiguration) {
        return new JobEventBus(jobEventConfiguration);
    }
}
