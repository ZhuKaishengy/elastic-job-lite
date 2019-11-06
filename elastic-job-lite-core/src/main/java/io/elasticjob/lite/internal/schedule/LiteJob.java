package io.elasticjob.lite.internal.schedule;

import io.elasticjob.lite.api.ElasticJob;
import io.elasticjob.lite.executor.JobExecutorFactory;
import io.elasticjob.lite.executor.JobFacade;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Objects;

/**
 * Lite调度作业.
 *
 * @author zhangliang
 */
public final class LiteJob implements Job {
    
    @Setter
    private ElasticJob elasticJob;
    
    @Setter
    private JobFacade jobFacade;

    @Setter
    private CoordinatorRegistryCenter regCenter;
    
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        if (Objects.isNull(context.getNextFireTime())) {
            // TODO 任务执行完毕，销毁任务相关对象
            JobExecutorFactory.getJobExecutor(elasticJob, jobFacade).removeInstance(regCenter);
            return;
        }
        JobExecutorFactory.getJobExecutor(elasticJob, jobFacade).execute();
    }
}
