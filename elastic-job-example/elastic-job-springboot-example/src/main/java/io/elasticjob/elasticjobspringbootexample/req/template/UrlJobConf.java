package io.elasticjob.elasticjobspringbootexample.req.template;

import io.elasticjob.elasticjobspringbootexample.req.JobConf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 18:41
 * @Description: job配置的封装，用于template api调用
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UrlJobConf extends JobConf {

    {
        super.setJobType("SIMPLE").setJobClass("io.elasticjob.elasticjobspringbootexample.job.template.UrlSimpleJob");
    }

    /**
     * url simple job 执行调用的url
     */
    @NotNull(message = "不能为空")
    private String executeUrl;

    /**
     * url simple job 执行调用请求类型
     * <p>
     *     get、post
     * </p>
     */
    @NotNull(message = "不能为空")
    private String requestType;
}
