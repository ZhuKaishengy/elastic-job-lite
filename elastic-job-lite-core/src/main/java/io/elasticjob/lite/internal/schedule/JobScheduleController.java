/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.elasticjob.lite.internal.schedule;

import io.elasticjob.lite.exception.JobSystemException;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.quartz.*;

import java.util.Date;

/**
 * 作业调度控制器.
 * 
 * @author zhangliang
 * @author zhukaishengy
 */
@RequiredArgsConstructor
public final class JobScheduleController {

    private final Scheduler scheduler;
    
    private final JobDetail jobDetail;
    
    private final String triggerIdentity;
    
    /**
     * 调度作业.
     * 
     * @param cron CRON表达式
     */
    public void scheduleJob(final String cron) {
        try {
            if (!scheduler.checkExists(jobDetail.getKey())) {
                scheduler.scheduleJob(jobDetail, createTrigger(cron));
            }
            scheduler.start();
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }

    /**
     * 调度作业.
     * @param delay 多长时间后开始第一次执行任务，单位为秒, 如果小于等于0，则立即启动任务
     * @param period 重试时间间隔,单位为秒,为0代表不重试
     * @param times 除第一次执行外的重试次数，如果为0则为无限次重试
     */
    public void scheduleJob(final int delay, int period, int times) {
        try {
            if (!scheduler.checkExists(jobDetail.getKey())) {
                scheduler.scheduleJob(jobDetail, createTrigger(delay, period, times));
            }
            scheduler.start();
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }

    /**
     * 重新调度作业.
     * 
     * @param cron CRON表达式
     */
    public synchronized void rescheduleJob(final String cron) {
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(triggerIdentity));
            if (!scheduler.isShutdown() && null != trigger && !cron.equals(trigger.getCronExpression())) {
                scheduler.rescheduleJob(TriggerKey.triggerKey(triggerIdentity), createTrigger(cron));
            }
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }

    private CronTrigger createTrigger(final String cron) {
        return TriggerBuilder.newTrigger().withIdentity(triggerIdentity).withSchedule(CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionDoNothing()).build();
    }

    private Trigger createTrigger(final int delay, int period, int times) {

        TriggerBuilder tb = TriggerBuilder.newTrigger().withIdentity(triggerIdentity);
        if(delay>0){
            Date startDate = DateTime.now().plusSeconds(delay).toDate();
            tb = tb.startAt(startDate);
        }else{
            tb = tb.startNow();
        }
        if(period > 0){
            SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(period);
            if(times > 0){
                ssb = ssb.withRepeatCount(times);
            } else {
                ssb = ssb.repeatForever();
            }
            tb = tb.withSchedule(ssb);
        }

        return tb.build();
    }

    /**
     * 判断作业是否暂停.
     * 
     * @return 作业是否暂停
     */
    public synchronized boolean isPaused() {
        try {
            return !scheduler.isShutdown() && Trigger.TriggerState.PAUSED == scheduler.getTriggerState(new TriggerKey(triggerIdentity));
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }
    
    /**
     * 暂停作业.
     */
    public synchronized void pauseJob() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.pauseAll();
            }
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }
    
    /**
     * 恢复作业.
     */
    public synchronized void resumeJob() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.resumeAll();
            }
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }
    
    /**
     * 立刻启动作业.
     */
    public synchronized void triggerJob() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.triggerJob(jobDetail.getKey());
            }
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }
    
    /**
     * 关闭调度器.
     */
    public synchronized void shutdown() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (final SchedulerException ex) {
            throw new JobSystemException(ex);
        }
    }
}
