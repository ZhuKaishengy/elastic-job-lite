package io.elasticjob.elasticjobspringbootexample.job;

import io.elasticjob.lite.api.ShardingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 17:22
 * @Description:
 */
@Slf4j
public class MBSimpleJobDemo extends BaseSimpleJob {
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        int shardingItem = shardingContext.getShardingItem();
        log.info("sharding {} 任务执行了：【 {} 】【 {} 】", shardingItem, Thread.currentThread().getName(), new Date());
    }
}
