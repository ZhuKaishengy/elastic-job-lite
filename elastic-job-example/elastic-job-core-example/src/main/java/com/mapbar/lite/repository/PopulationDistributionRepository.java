package com.mapbar.lite.repository;

import com.mapbar.lite.entity.PopulationDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 13:07
 * @Description: 操作人口统计的实体类
 */
public final class PopulationDistributionRepository {

    /**
     * prepare data
     */
    private static Map<Integer, PopulationDistribution> dataMap = new ConcurrentHashMap<>(300);
    static {
        addData(0, 50, "beijing");
        addData(51, 100, "shanghai");
        addData(101, 300, "guangzhou");
    }
    private static void addData(int fromIndex, int toIndex, String location) {
        for (int i = fromIndex; i < toIndex; i++) {
            PopulationDistribution item = new PopulationDistribution().setId(i).setCity(location)
                    .setNumber(1L).setStatus(PopulationDistribution.Pstatus.UNCOUNTED);
            dataMap.put(i, item);
        }
    }

    public List<PopulationDistribution> findTodoData(String location, int count) {
        List<PopulationDistribution> result = new ArrayList<>(300);
        for (PopulationDistribution item : dataMap.values()) {
            if (result.size() >= count) {
                return result;
            }
            if (Objects.nonNull(item.getStatus()) && item.getStatus().equals(PopulationDistribution.Pstatus.UNCOUNTED)
                    && location.equals(item.getCity())) {
                result.add(item);
            }
        }
        return result;
    }

    public void accomplishData(Integer id) {
        dataMap.get(id).setStatus(PopulationDistribution.Pstatus.COUNTED);
    }
}

