package io.elasticjob.elasticjobspringbootexample.listener;


import io.elasticjob.lite.api.listener.ElasticJobListener;
import io.elasticjob.lite.executor.ShardingContexts;

import java.util.Map;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/22 11:12
 * @Description:
 */
public class MyListener implements ElasticJobListener {

    /**
     * 作业执行前的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        Map<Integer, String> shardingItemParameters = shardingContexts.getShardingItemParameters();
        System.out.println("before ..." + shardingItemParameters);
    }

    /**
     * 作业执行后的执行的方法.
     *
     * @param shardingContexts 分片上下文
     */
    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        Map<Integer, String> shardingItemParameters = shardingContexts.getShardingItemParameters();
        System.out.println("after ..." + shardingItemParameters);
    }
}
