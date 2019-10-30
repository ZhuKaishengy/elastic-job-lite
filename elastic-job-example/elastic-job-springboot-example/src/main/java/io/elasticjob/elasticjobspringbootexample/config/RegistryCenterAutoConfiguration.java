package io.elasticjob.elasticjobspringbootexample.config;

import io.elasticjob.elasticjobspringbootexample.properties.ZookeeperConfigurationProperties;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import io.elasticjob.lite.reg.zookeeper.ZookeeperConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 16:05
 * @Description: 注册中心配置类
 */
@Configuration
@EnableConfigurationProperties({ZookeeperConfigurationProperties.class})
@ConditionalOnMissingBean(value = {CoordinatorRegistryCenter.class})
public class RegistryCenterAutoConfiguration {

    @Autowired
    private ZookeeperConfigurationProperties zookeeperConfigurationProperties;

    /**
     * elasticjob 抽象了注册中心接口 {@link io.elasticjob.lite.reg.base.RegistryCenter}
     * 提供了默认的实现类{@link ZookeeperRegistryCenter}
     * 此方法基于配置文件对zk注册中心完成自动配置
     * @return ZookeeperRegistryCenter
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(zookeeperConfigurationProperties.getServerLists(),
                zookeeperConfigurationProperties.getNamespace());
        BeanUtils.copyProperties(zookeeperConfigurationProperties, zkConfig);
        return new ZookeeperRegistryCenter(zkConfig);
    }
}
