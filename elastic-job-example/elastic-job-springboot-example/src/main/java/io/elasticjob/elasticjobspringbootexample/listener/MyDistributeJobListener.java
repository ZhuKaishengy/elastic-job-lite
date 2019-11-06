package io.elasticjob.elasticjobspringbootexample.listener;

import io.elasticjob.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import io.elasticjob.lite.executor.ShardingContexts;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/31 10:52
 * @Description:
 */
public class MyDistributeJobListener extends AbstractDistributeOnceElasticJobListener {

    public MyDistributeJobListener(long startedTimeoutMilliseconds, long completedTimeoutMilliseconds) {
        super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
    }
    public MyDistributeJobListener() {
        super(0, 0);
    }

    /**
     * 分布式环境中最后一个作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void doBeforeJobExecutedAtLastStarted(ShardingContexts shardingContexts) {
        System.out.println("distributed listener before ...");
    }

    /**
     * 分布式环境中最后一个作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void doAfterJobExecutedAtLastCompleted(ShardingContexts shardingContexts) {
        System.out.println("distributed listener after ...");
    }
}
