package com.mapbar.lite.job;

import com.mapbar.lite.entity.PopulationDistribution;
import com.mapbar.lite.repository.PopulationDistributionRepository;
import com.mapbar.lite.repository.PopulationDistributionRepositoryFactory;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.simple.SimpleJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/16 16:46
 * @Description:
 */
public class MySimpleJob implements SimpleJob {

    private PopulationDistributionRepository repository = PopulationDistributionRepositoryFactory.getRepository();
    /**
     * 执行作业.
     *
     * @param shardingContext 分片上下文
     */
    @Override
    public void execute(ShardingContext shardingContext) {
        String city = shardingContext.getShardingParameter();
        List<PopulationDistribution> todoData = repository.findTodoData(city, 10);
        for (PopulationDistribution todoDatum : todoData) {
            repository.accomplishData(todoDatum.getId());
        }
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(),
                new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));
    }
}
