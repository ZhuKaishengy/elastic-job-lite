package io.elasticjob.elasticjobspringbootexample.api;

import io.elasticjob.elasticjobspringbootexample.req.JobConf;
import io.elasticjob.elasticjobspringbootexample.resp.ServiceResult;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 17:29
 * @Description: 分布式任务调度对外暴露的api
 */
@RequestMapping(JobApi.JOB_PREFIX)
public interface JobApi {

    String JOB_PREFIX = "/job";

    /**
     * 添加一个作业
     * @param jobConf
     * @return 任务是否注册成功
     */
    @PostMapping("/simple/add")
    ServiceResult addSimpleJob(@RequestBody JobConf jobConf);

    /**
     * 添加一个作业
     * @param jobConf
     * @return 任务是否注册成功
     */
    @PostMapping("/dataflow/add")
    ServiceResult addDataFlowJob(@RequestBody JobConf jobConf);

    /**
     * 添加一个作业
     * @param jobConf
     * @return 任务是否注册成功
     */
    @PostMapping("/script/add")
    ServiceResult addScriptJob(@RequestBody JobConf jobConf);

    /**
     * 删除一个作业
     * @param jobName
     * @return
     */
    @GetMapping("/remove/{jobName}")
    ServiceResult removeJob(@PathVariable String jobName);

}
