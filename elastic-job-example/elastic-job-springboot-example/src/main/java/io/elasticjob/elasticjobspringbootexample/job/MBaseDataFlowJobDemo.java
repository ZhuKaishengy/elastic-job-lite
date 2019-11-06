package io.elasticjob.elasticjobspringbootexample.job;

import io.elasticjob.lite.api.ShardingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/31 13:10
 * @Description:
 */
@Slf4j
public class MBaseDataFlowJobDemo extends BaseDataFlowJob{

    private static List<String> data = new ArrayList<>(10);
    static {
        data.add(0, "a");
        data.add(1, "b");
        data.add(2, "c");
        data.add(3, "d");
        data.add(4, "e");
        data.add(5, "f");
        data.add(6, "g");
        data.add(7, "h");
        data.add(8, "i");
        data.add(9, "j");
    }

    /**
     * 获取待处理数据.
     *
     * @param shardingContext 分片上下文
     * @return 待处理的数据集合
     */
    @Override
    public List fetchData(ShardingContext shardingContext) {
        int item = shardingContext.getShardingItem();
        List<String> result = new ArrayList<>();
        if (item == 0) {
            for (int i = 0; i < data.size(); i++) {
                if (i % 2 != 0) {
                    result.add(data.get(i));
                }
            }
        }
        if (item == 1) {
            for (int i = 0; i < data.size(); i++) {
                if (i % 2 == 0) {
                    result.add(data.get(i));
                }
            }
        }
        return result;
    }

    /**
     * 处理数据.
     *
     * @param shardingContext 分片上下文
     * @param data            待处理数据集合
     */
    @Override
    public void processData(ShardingContext shardingContext, List data) {
        int shardingItem = shardingContext.getShardingItem();
        log.info("shardingItem:{}, data:{}", shardingItem, data);
    }
}
