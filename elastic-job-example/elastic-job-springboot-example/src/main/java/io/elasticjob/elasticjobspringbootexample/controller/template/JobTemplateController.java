package io.elasticjob.elasticjobspringbootexample.controller.template;

import io.elasticjob.elasticjobspringbootexample.api.template.JobTemplateApi;
import io.elasticjob.elasticjobspringbootexample.req.template.UrlJobConf;
import io.elasticjob.elasticjobspringbootexample.resp.ServiceResult;
import io.elasticjob.elasticjobspringbootexample.service.impl.CustomJobServiceImpl;
import io.elasticjob.elasticjobspringbootexample.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 17:28
 * @Description: job common api 实现
 */
@Slf4j
@RestController
public class JobTemplateController implements JobTemplateApi {

    @Autowired
    private CustomJobServiceImpl jobService;

    /**
     * 创建一个url job，用于接口调用
     *
     * @param jobConf
     * @return 任务是否注册成功
     */
    @Override
    public ServiceResult newUrlJob(@RequestBody UrlJobConf jobConf) {
        jobConf.setJobParameter(jobConf.getRequestType() + "," + jobConf.getExecuteUrl());
        ValidateUtil.validateEntity(jobConf);
        jobService.createJob(jobConf);
        return ServiceResult.successWithDefaultMsg();
    }

    /**
     * 用于url job测试
     *  模拟耗时20ms
     * @return
     */
    @Override
    public ServiceResult forTest() {
        try {
            Thread.sleep(20);
            log.info("任务执行完毕。。。");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ServiceResult.successWithDefaultMsg();
    }
}
