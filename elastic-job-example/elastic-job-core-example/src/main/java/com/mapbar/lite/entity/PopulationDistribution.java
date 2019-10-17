package com.mapbar.lite.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 13:00
 * @Description: 各城市人口分布实体类
 */
@Data
@Accessors(chain = true)
public class PopulationDistribution {
    /**
     * 唯一标示
     */
    private Integer id;
    /**
     * 城市
     */
    private String city;
    /**
     * 人口数量
     */
    private Long number;
    /**
     * 统计状态
     */
    private Pstatus status;

    public enum Pstatus{
        /**
         * 已统计
         */
        COUNTED(1, "已统计"),
        /**
         * 未统计
         */
        UNCOUNTED(0, "未统计");

        private int mark;
        private String msg;

        Pstatus(int mark, String msg) {
            this.mark = mark;
            this.msg = msg;
        }
    }
}
