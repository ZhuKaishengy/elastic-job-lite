package io.elasticjob.elasticjobspringbootexample.controller;

import io.elasticjob.elasticjobspringbootexample.api.JobCommonApi;
import io.elasticjob.elasticjobspringbootexample.req.JobConf;
import io.elasticjob.elasticjobspringbootexample.resp.ServiceResult;
import io.elasticjob.elasticjobspringbootexample.service.impl.CustomJobServiceImpl;
import io.elasticjob.elasticjobspringbootexample.util.ValidateUtil;
import io.elasticjob.lite.api.JobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 17:28
 * @Description: job common api 实现
 */
@RestController
public class JobCommonController implements JobCommonApi {

    @Autowired
    private CustomJobServiceImpl jobService;
    /**
     * 添加一个简单作业
     *
     * @param jobConf
     * @return ServiceResult
     */
    @Override
    public ServiceResult addSimpleJob(@RequestBody JobConf jobConf) {

        ValidateUtil.validateEntity(jobConf);
        jobConf.setJobType(JobType.SIMPLE.name());
        jobService.createJob(jobConf);
        return ServiceResult.successWithDefaultMsg();
    }

    /**
     * 添加一个作业
     *
     * @param jobConf
     * @return 任务是否注册成功
     */
    @Override
    public ServiceResult addDataFlowJob(@RequestBody JobConf jobConf) {

        ValidateUtil.validateEntity(jobConf);
        jobConf.setJobType(JobType.DATAFLOW.name());
        jobService.createJob(jobConf);
        return ServiceResult.successWithDefaultMsg();
    }

    /**
     * 添加一个作业
     *
     * @param jobConf
     * @return 任务是否注册成功
     */
    @Override
    public ServiceResult addScriptJob(@RequestBody JobConf jobConf) {

        ValidateUtil.validateEntity(jobConf);
        jobConf.setJobType(JobType.SCRIPT.name());
        jobService.createJob(jobConf);
        return ServiceResult.successWithDefaultMsg();
    }

    /**
     * 删除一个作业
     *
     * @param jobName
     * @return
     */
    @Override
    public ServiceResult removeJob(@PathVariable String jobName) {

        jobService.removeJob(jobName);
        return ServiceResult.successWithDefaultMsg();
    }
}
