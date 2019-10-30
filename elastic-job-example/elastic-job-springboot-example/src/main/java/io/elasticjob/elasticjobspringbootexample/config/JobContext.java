package io.elasticjob.elasticjobspringbootexample.config;

import io.elasticjob.elasticjobspringbootexample.service.CustomJobService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/22 11:38
 * @Description: spring启动初始化监听器
 */
//@Component
public class JobContext implements InitializingBean {

    @Autowired
    private CustomJobService jobService;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 开启任务监听,当有任务添加时，监听zk中的数据增加，自动在其他节点也初始化该任务
        jobService.monitorJobRegister();
    }
}
