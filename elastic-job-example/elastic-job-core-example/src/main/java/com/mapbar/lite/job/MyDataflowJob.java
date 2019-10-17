package com.mapbar.lite.job;

import com.mapbar.lite.entity.PopulationDistribution;
import com.mapbar.lite.repository.PopulationDistributionRepository;
import com.mapbar.lite.repository.PopulationDistributionRepositoryFactory;
import io.elasticjob.lite.api.ShardingContext;
import io.elasticjob.lite.api.dataflow.DataflowJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 14:13
 * @Description:
 */
public class MyDataflowJob implements DataflowJob<PopulationDistribution> {

    private PopulationDistributionRepository repository = PopulationDistributionRepositoryFactory.getRepository();
    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List<PopulationDistribution> fetchData(ShardingContext shardingContext) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW FETCH"));
        String city = shardingContext.getShardingParameter();
        return repository.findTodoData(city, 10);
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data            待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List<PopulationDistribution> data) {
        System.out.println(String.format("Item: %s | Time: %s | Thread: %s | %s",
                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "DATAFLOW PROCESS"));
        data.forEach(p -> repository.accomplishData(p.getId()));
    }
}
