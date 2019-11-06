package io.elasticjob.lite.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 任务调度类型
 * @auther zhukaishengy
 */
@RequiredArgsConstructor
public enum ScheduleTypeEnum {

    /**
     * 简单任务调度类型
     *  {@link io.elasticjob.lite.internal.schedule.JobScheduleController#scheduleJob(int, int, int)}
     */
    SIMPLE("SIMPLE"),

    /**
     * 使用cron表达式调度任务
     * {@link io.elasticjob.lite.internal.schedule.JobScheduleController#scheduleJob(String)}
     */
    CRON("CRON");

    @Getter
    private final String value;
}
