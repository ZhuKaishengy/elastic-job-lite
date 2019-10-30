package io.elasticjob.elasticjobspringbootexample.service;

import io.elasticjob.elasticjobspringbootexample.req.JobConf;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 16:54
 * @Description:
 */
public interface CustomJobService {

    /**
     * 创建任务
     * @param jobConf
     */
    void createJob(JobConf jobConf);

    /**
     * 删除任务
     * @param jobName
     */
    void removeJob(String jobName);

    /**
     * 监控任务的注册
     */
    void monitorJobRegister();
}
