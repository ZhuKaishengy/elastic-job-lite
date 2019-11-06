package io.elasticjob.elasticjobspringbootexample.api.template;

import io.elasticjob.elasticjobspringbootexample.req.template.UrlJobConf;
import io.elasticjob.elasticjobspringbootexample.resp.ServiceResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 17:29
 * @Description: 分布式任务调度对外暴露的api
 * <p>
 *     根据不同业务定制api
 * </p>
 */
@RequestMapping(JobTemplateApi.JOB_PREFIX)
public interface JobTemplateApi {

    String JOB_PREFIX = "/job";

    /**
     * 创建一个简单作业，用于接口调用
     * @param jobConf
     * @return 任务是否注册成功
     */
    @PostMapping("/url/add")
    ServiceResult newUrlJob(@RequestBody UrlJobConf jobConf);

    /**
     * 用于url job测试
     * @return
     */
    @GetMapping("/test")
    ServiceResult forTest();
}
