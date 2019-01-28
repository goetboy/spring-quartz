package pers.goetboy.quartz.common.util;

import pers.goetboy.quartz.job.jobfactory.AccessMonitorJobFactory;

import pers.goetboy.quartz.model.entity.ScheduleJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调度工具类
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
public class ScheduleUtils {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ScheduleUtils.class);

    /**
     * 获取触发器key.
     * 获取调度任务的唯一key
     * key值包含两个信息
     * jobName 和jobGroup
     * 两个值组合在一起必须是唯一
     *
     * @param jobName  job名称
     * @param jobGroup job分组
     * @return 调度key值
     */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup) {

        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /**
     * 获取表达式触发器.
     *
     * @param scheduler Quartz调度器
     * @param jobName   the job name
     * @param jobGroup  the job group
     * @return cron trigger
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            logger.error("获取定时任务CronTrigger出现异常", e);
            throw new SchedulerException("获取定时任务CronTrigger出现异常");
        }
    }

    /**
     * 创建任务
     *
     * @param scheduler   the scheduler
     * @param scheduleJob the schedule job
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        createScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(), scheduleJob.getUrl(),
                scheduleJob.getCronExpression(), scheduleJob);
    }

    /**
     * 创建定时任务.
     * 将job 挂靠至定时任务中.
     *
     * @param scheduler      the scheduler
     * @param jobName        the job name
     * @param jobGroup       the job group
     * @param cronExpression the cron expression
     * @param param          the param
     * @see pers.goetboy.quartz.job.jobfactory.JobFactory job执行工厂接口
     * @see pers.goetboy.quartz.job.jobfactory.AccessMonitorJobFactory  数据监控job执行工厂
     */
    public static void createScheduleJob(Scheduler scheduler, String jobName, String jobGroup, String clazzName,
                                         String cronExpression, Object param) throws SchedulerException {
        //这里可以因不同job执行器的需要做调整 目前只有异常监控job执行器所以可以直接设置
        final Class<? extends Job> jobClass = AccessMonitorJobFactory.class;
        try {
            //做下任务类校验，如果没有找到任务类 则抛出异常
            Class.forName(clazzName);
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();

            String jobTrigger = trigger.getKey().getName();

            ScheduleJob scheduleJob = (ScheduleJob) param;
            scheduleJob.setJobTrigger(jobTrigger);

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);

            logger.info("created job:" + jobDetail.toString());
        } catch (ClassNotFoundException e) {
            throw new SchedulerException("没有找到任务类");
        } catch (SchedulerException e) {
            logger.error("创建定时任务失败", e);
            throw new SchedulerException("创建定时任务失败");
        }
    }

    /**
     * 运行一次任务
     *
     * @param scheduler Quartz调度器
     * @param jobName   job名称
     * @param jobGroup  job分组
     */
    public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("运行一次定时任务失败", e);
            throw new SchedulerException("运行一次定时任务失败");
        }
    }

    /**
     * 暂停任务.
     * 将正在执行的任务暂停
     *
     * @param scheduler Quartz调度器
     * @param jobName   job名称
     * @param jobGroup  job分组
     */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("暂停定时任务失败", e);
            throw new SchedulerException("暂停定时任务失败");
        }
    }

    /**
     * 恢复任务状态。
     * 将暂停中的任务恢复到正常状态。
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("恢复定时任务失败", e);
            throw new SchedulerException("恢复定时任务失败");
        }
    }

    /**
     * 获取jobKey
     *
     * @param jobName  the job name
     * @param jobGroup the job group
     * @return the job key
     */
    public static JobKey getJobKey(String jobName, String jobGroup) {

        return JobKey.jobKey(jobName, jobGroup);
    }

    /**
     * 更新定时任务
     *
     * @param scheduler   the scheduler
     * @param scheduleJob the schedule job
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
        updateScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
                scheduleJob.getCronExpression(), scheduleJob.getIsSync(), scheduleJob);
    }

    /**
     * 更新定时任务
     *
     * @param scheduler      the scheduler
     * @param jobName        the job name
     * @param jobGroup       the job group
     * @param cronExpression the cron expression
     * @param isSync         the is sync
     * @param param          the param
     */
    public static void updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
                                         String cronExpression, boolean isSync, Object param) throws SchedulerException {


        try {

            TriggerKey triggerKey = ScheduleUtils.getTriggerKey(jobName, jobGroup);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

        } catch (SchedulerException e) {
            logger.error("更新定时任务失败", e);
            throw new SchedulerException("更新定时任务失败");
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败", e);
            throw new SchedulerException("删除定时任务失败");
        }
    }
}
