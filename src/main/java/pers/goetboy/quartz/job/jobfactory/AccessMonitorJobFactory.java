package pers.goetboy.quartz.job.jobfactory;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import pers.goetboy.common.SpringContextUtil;
import pers.goetboy.common.exception.CommonsAssistantException;
import pers.goetboy.common.mail.Config;
import pers.goetboy.common.mail.MailHelper;
import pers.goetboy.quartz.common.exception.SchedulerException;
import pers.goetboy.quartz.constant.SCHEDULE_JOB_LOG_STATE;
import pers.goetboy.quartz.job.AccessMonitorBaseJob;
import pers.goetboy.quartz.model.Result;
import pers.goetboy.quartz.model.entity.ScheduleJob;
import pers.goetboy.quartz.model.entity.ScheduleJobLog;
import pers.goetboy.quartz.model.vo.ScheduleJobVo;
import pers.goetboy.quartz.services.ScheduleJobLogService;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * 数据监控任务工厂.
 * 所有数据监控任务均通过此类执行.
 *
 * @author:goetboy;
 * @date 2018 /12 /27
 * @see AccessMonitorBaseJob 数据监控任务基类 所有数据监控类均通过此类派生
 **/
@Component
public class AccessMonitorJobFactory extends QuartzJobBean implements JobFactory {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(AccessMonitorJobFactory.class);
    /**
     * 邮件配置
     */
    private Config emailConfig;
    /**
     * 邮件助手
     */
    private MailHelper mailHelper;

    private ScheduleJobLogService scheduleJobLogService;

    public AccessMonitorJobFactory() {

    }

    @Autowired
    public AccessMonitorJobFactory(Config emailConfig) {
        this.emailConfig = emailConfig;
    }

    /**
     * 初始化job执行器
     * 初始化，job执行器中相关对象
     * {@value }
     */
    public void init() {
        scheduleJobLogService = SpringContextUtil.getBean(ScheduleJobLogService.class);
        emailConfig = SpringContextUtil.getBean(Config.class);
        mailHelper = new MailHelper();
    }

    /**
     * job执行方法.
     * 数据库异常监控相关的job会通过此方法执行
     *
     * @param context 调度信息
     * @throws JobExecutionException job执行出现异常
     */

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        init();
        ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
        logger.info("AsyncJobFactory execute");
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJobVo.JOB_PARAM_KEY);
        logger.info("jobName:{}", scheduleJob.getJobName());
        scheduleJobLog.setScheduleJob(scheduleJob);
        try {
            Class clazz = Class.forName(scheduleJob.getUrl());
            if (AccessMonitorBaseJob.class.isAssignableFrom(clazz.getSuperclass())) {
                AccessMonitorBaseJob accessMonitorBaseJob = (AccessMonitorBaseJob) clazz.newInstance();
                //执行初始化方法
                accessMonitorBaseJob.initJob();
                //执行业务方法
                Result result = accessMonitorBaseJob.execute((ScheduleJob) context.get(ScheduleJob.JOB_PARAM_KEY));
                if (Result.ERROR.equals(result.getStatus())) {
                    scheduleJobLog.setInfo("任务脚本检测到异常数据:[" + result.getMsg() + "]");
                    sendEmail(scheduleJob.getEmail(), "任务脚本[" + scheduleJob.getJobName() + "]检测到异常数据", result.getMsg());
                }
                if (Result.SUCCESS.equals(result.getStatus())) {
                    scheduleJobLog.setInfo("执行成功");
                }
            } else {
                throw new JobExecutionException("此类不符合任务类规范");
            }
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.ok.getValue());
        } catch (ClassNotFoundException e) {
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.error.getValue());
            scheduleJobLog.setInfo("没有找到任务类[" + scheduleJob.getUrl() + "]:" + e.toString());
            throw new JobExecutionException("没有找到任务类", new SchedulerException("没有找到任务类[" + scheduleJob.getUrl() + "]"));
        } catch (JobExecutionException e) {
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.error.getValue());
            scheduleJobLog.setInfo(e.toString());
            throw e;
        } catch (IllegalAccessException e) {
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.error.getValue());
            scheduleJobLog.setInfo("构造方法私有化，无法创建对象" + e.toString());
            throw new JobExecutionException("构造方法私有化，无法创建对象", e);
        } catch (InstantiationException e) {
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.error.getValue());
            scheduleJobLog.setInfo("对象实例化失败" + e.toString());
            throw new JobExecutionException("对象实例化失败", e);
        } catch (SchedulerException e) {
            scheduleJobLog.setState(SCHEDULE_JOB_LOG_STATE.error.getValue());
            scheduleJobLog.setInfo("对象执行异常" + e.toString());
            throw new JobExecutionException("对象执行异常", e);
        } finally {
            logger.info("AsyncJobFactory end");
            scheduleJobLog.setEndTime(new Date());
            try {
                scheduleJobLogService.save(scheduleJobLog);
            } catch (CommonsAssistantException e) {
                logger.error("日志记录异常", e);
            }
        }
    }


    /**
     * 邮件发送.
     * job执行出现问题，或者job需要发送邮件时调用此方法
     * 此方法执行需要初始化相关对象 初始化方法参见{@link #init()}
     *
     * @param sender  接收人
     * @param subject 邮件标题
     * @param text    邮件内容
     */
    protected void sendEmail(String sender, String subject, String text) {
        try {
            mailHelper.setConfig(emailConfig);
            if (StringUtils.isEmpty(subject)) {
                subject = "脚本执行出现问题:";
            }
            mailHelper.setSubject(subject).
                    setSender(sender).
                    setText(text).
                    send();
        } catch (MessagingException e) {
            logger.error("邮件发送异常", e);
        }
    }


}
