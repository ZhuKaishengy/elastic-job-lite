package io.elasticjob.elasticjobspringbootexample.service.impl;

import io.elasticjob.elasticjobspringbootexample.exception.BizException;
import io.elasticjob.elasticjobspringbootexample.exception.BizExceptionEnum;
import io.elasticjob.elasticjobspringbootexample.req.JobConf;
import io.elasticjob.elasticjobspringbootexample.service.CustomJobService;
import io.elasticjob.elasticjobspringbootexample.util.JsonUtils;
import io.elasticjob.elasticjobspringbootexample.util.ReflectionUtils;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.api.JobType;
import io.elasticjob.lite.api.listener.ElasticJobListener;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.JobTypeConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.ScheduleTypeEnum;
import io.elasticjob.lite.config.dataflow.DataflowJobConfiguration;
import io.elasticjob.lite.config.script.ScriptJobConfiguration;
import io.elasticjob.lite.config.simple.SimpleJobConfiguration;
import io.elasticjob.lite.event.JobEventBus;
import io.elasticjob.lite.executor.handler.JobProperties;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 16:56
 * @Description:
 */
@Slf4j
@Service
public class CustomJobServiceImpl implements CustomJobService, ApplicationContextAware {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @Autowired
    private JobEventBus jobEventBus;

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    @Override
    public void createJob(JobConf jobConf) {

        JobCoreConfiguration coreConfig = buildJobCoreConfiguration(jobConf);
        LiteJobConfiguration liteJobConfig = buildLiteJobConfiguration(jobConf, coreConfig);

        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(JobScheduler.class);
        factory.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        factory.addConstructorArgValue(regCenter);
        factory.addConstructorArgValue(liteJobConfig);
        factory.addConstructorArgValue(jobEventBus);

        List<ElasticJobListener> elasticJobListeners = getTargetElasticJobListeners(jobConf);
        factory.addConstructorArgValue(elasticJobListeners);

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        beanFactory.registerBeanDefinition("JobScheduler" + jobConf.getJobName(), factory.getBeanDefinition());
        JobScheduler jobScheduler = (JobScheduler) ctx.getBean("JobScheduler" + jobConf.getJobName());
        jobScheduler.init();
        log.info("[ 创建任务 ]:{} 成功！", jobConf.getJobName());
    }

    private List<ElasticJobListener> getTargetElasticJobListeners(JobConf job) {
        // TODO DistributedJobListener 的处理
        String listeners = job.getListener();
        List<ElasticJobListener> result = new ArrayList<>(listeners.length());
        if (StringUtils.isEmpty(listeners)) {
            return result;
        }
        String[] splits = listeners.split(",");
        for (String listener : splits) {
            Object instance = ReflectionUtils.getInstance(listener);
            if (instance instanceof ElasticJobListener) {
                result.add((ElasticJobListener)instance);
            }
        }
        return result;
    }

    /**
     * 删除任务
     * @param jobName
     */
    @Override
    public void removeJob(String jobName) {
        try {
            regCenter.remove("/" + jobName);
        } catch (Exception e) {
            throw new BizException(BizExceptionEnum.BIZ_EXCEPTION.getErrorCode(),
                    e.getMessage());
        }
    }


    private JobCoreConfiguration buildJobCoreConfiguration(JobConf job) {
        // 核心配置
        String scheduleType = job.getScheduleType();
        JobCoreConfiguration.Builder builder = null ;
        if (scheduleType.equals(ScheduleTypeEnum.CRON.getValue())) {
            builder = JobCoreConfiguration.newBuilder(job.getJobName(), job.getCron(), job.getShardingTotalCount());
        }
        if (scheduleType.equals(ScheduleTypeEnum.SIMPLE.getValue())) {
            builder = JobCoreConfiguration.newBuilder(job.getJobName(), job.getShardingTotalCount(), job.getDelay(),
                    job.getPeriod(), job.getTimes());
        }
        assert builder != null;
        return builder.shardingItemParameters(job.getShardingItemParameters())
                        .description(job.getDescription())
                        .failover(job.isFailover())
                        .jobParameter(job.getJobParameter())
                        .misfire(job.isMisfire())
                        .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), job.getJobProperties().getJobExceptionHandler())
                        .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), job.getJobProperties().getExecutorServiceHandler())
                        .build();
    }

    private LiteJobConfiguration buildLiteJobConfiguration(JobConf jobConf, JobCoreConfiguration coreConfig) {

        String jobType = jobConf.getJobType();
        JobTypeConfiguration jobTypeConfiguration = null;
        LiteJobConfiguration liteJobConfiguration;

        if (jobType.equals(JobType.SIMPLE.name())) {
            jobTypeConfiguration = new SimpleJobConfiguration(coreConfig, jobConf.getJobClass());
        }
        if (jobType.equals(JobType.DATAFLOW.name())) {
            jobTypeConfiguration = new DataflowJobConfiguration(coreConfig, jobConf.getJobClass(), jobConf.isStreamingProcess());
        }
        if (jobType.equals(JobType.SCRIPT.name())) {
            String scriptCommandLine = jobConf.getScriptCommandLine();
            if (StringUtils.isEmpty(scriptCommandLine)) {
                throw new BizException(BizExceptionEnum.LACK_SCRIPTCOMMANDLINE.getErrorCode(),
                        BizExceptionEnum.LACK_SCRIPTCOMMANDLINE.getErrordesc());
            }
            jobTypeConfiguration = new ScriptJobConfiguration(coreConfig, buildScriptCommandLine(scriptCommandLine));
        }
        liteJobConfiguration = LiteJobConfiguration.newBuilder(jobTypeConfiguration)
                .overwrite(jobConf.isOverwrite())
                .disabled(jobConf.isDisabled())
                .monitorPort(jobConf.getMonitorPort())
                .monitorExecution(jobConf.isMonitorExecution())
                .maxTimeDiffSeconds(jobConf.getMaxTimeDiffSeconds())
                .jobShardingStrategyClass(jobConf.getJobShardingStrategyClass())
                .reconcileIntervalMinutes(jobConf.getReconcileIntervalMinutes())
                // 将监听保存在 ElasticJob对象中
                .listenerClass(jobConf.getListener())
                .distributedListenerClass(jobConf.getDistributedListener())
                .build();
        return liteJobConfiguration;
    }

    private static final String FILE_PERMISSION_RWX = "rwxr-xr-x";

    private String buildScriptCommandLine(String absolutePath) {

        Path path = Paths.get(absolutePath);
        try {
            Files.setPosixFilePermissions(path, PosixFilePermissions.fromString(FILE_PERMISSION_RWX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toString();
    }

    /**
     * 开启任务监听
     * 当有任务添加时，监听zk中的数据增加，自动在其他节点也初始化该任务；
     * 当有任务删除时，监听zk中的数据减少，自动在其他节点也将任务从spring中移除
     */
    @Override
    public void monitorJobRegister() {
        CuratorFramework client = regCenter.getClient();
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/", true);
        PathChildrenCacheListener childrenCacheListener = (client1, event) -> {
            ChildData data = event.getData();
            switch (event.getType()) {
                case CHILD_ADDED:
                    String config = new String(client1.getData().forPath(data.getPath() + "/config"));
                    JobConf job = JsonUtils.toBean(JobConf.class, config);
                    Object bean = null;
                    // 获取bean失败则添加任务
                    try {
                        bean = ctx.getBean("JobScheduler" + job.getJobName());
                    } catch (BeansException e) {
                        log.info("在此节点初始化任务【 {} 】!", job.getJobName());
                    }
                    if (Objects.isNull(bean)) {
                        createJob(job);
                    }
                    break;
                case CHILD_REMOVED:
                    String jobName = data.getPath().substring(1);
                    Object jobBean = ctx.getBean("JobScheduler" + jobName);
                    if (Objects.nonNull(jobBean)) {
                        log.info("监听到任务【 {} 】移除！", jobName);
                        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
                        defaultListableBeanFactory.removeBeanDefinition("JobScheduler" + jobName);
                    }
                    break;
                default:
                    break;
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        try {
            childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            throw new BizException(BizExceptionEnum.BIZ_EXCEPTION.getErrorCode(),
                    e.getMessage());
        }
    }

}
