package pers.goetboy.quartz.services;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.goetboy.common.bean.BeanConverter;
import pers.goetboy.common.exception.service.ServiceTipsException;
import pers.goetboy.quartz.common.util.ScheduleUtils;
import pers.goetboy.quartz.constant.TRIGGER_STATE_ENUM;
import pers.goetboy.quartz.mapper.ScheduleJobMapper;
import pers.goetboy.quartz.model.entity.ScheduleJob;
import pers.goetboy.quartz.model.vo.ScheduleJobVo;

import java.util.ArrayList;
import java.util.List;

/**
 * job业务类.
 * job相关的业务操作均在此实现
 * 包括 实时的job添加/删除/开始/暂停等操作
 * 以及 job的存库操作
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 * @see ScheduleUtils job工具类
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class ScheduleJobService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobService.class);
    /**
     * 调度工厂Bean
     */
    private final Scheduler scheduler;

    private final ScheduleJobMapper scheduleJobMapper;

    @Autowired
    public ScheduleJobService(Scheduler scheduler, ScheduleJobMapper scheduleJobMapper) {
        this.scheduler = scheduler;
        this.scheduleJobMapper = scheduleJobMapper;
    }


    /**
     * 初始化job。
     * 在servlet初始化时执行，将数据库中保存的job装载至内存.
     *
     * @throws SchedulerException job配置过程中产生异常，异常详情参见
     *                            {@link ScheduleUtils#getCronTrigger(Scheduler, String, String)}
     *                            {@link ScheduleUtils#createScheduleJob(Scheduler, ScheduleJob)}
     */
    public void initScheduleJob() throws SchedulerException {
        List<ScheduleJob> scheduleJobList = scheduleJobMapper.selectList(null);
        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }
        for (ScheduleJob scheduleJob : scheduleJobList) {

            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());

            //不存在，创建一个
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);

            } else {
                //已存在，那么更新相应的定时设置
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    /**
     * 保存job信息.
     * <p>
     * job的group如果为空 会分配至默认分组 "DEFAULT"
     * 将job的配置保存至数据库,
     * 将job配置至内存
     *
     * @param scheduleJobVo
     * @return
     * @throws Exception
     */
    public Integer insert(ScheduleJobVo scheduleJobVo) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        if (StringUtils.isEmpty(scheduleJob.getJobGroup())) {
            scheduleJob.setJobGroup(Scheduler.DEFAULT_GROUP);
        }
        scheduleJob.setAliasName(scheduleJob.getJobName());

        Integer id = scheduleJobMapper.insert(scheduleJob);

        scheduleJob.setScheduleJobId(id);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);

        return id;
    }

    /**
     * 更新job信息.
     * <p>
     * 更新内存中的job配置信息
     * 更新数据库中的job配置信息,
     *
     * @param scheduleJobVo job对象
     * @return null
     */
    public void update(ScheduleJobVo scheduleJobVo) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        scheduleJob.setAliasName(scheduleJob.getJobName());
        scheduleJob.setJobGroup(Scheduler.DEFAULT_GROUP);
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        scheduleJobMapper.updateById(scheduleJob);
    }

    /**
     * 删除后创建job.
     * 内存中的job会先删除再创建，数据库中的信息会直接更新
     *
     * @param scheduleJobVo
     */
    public void deleteUpdate(ScheduleJobVo scheduleJobVo) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobVo.getTargetObject(ScheduleJob.class);
        //先删除
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //再创建
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        //数据库直接更新即可
        scheduleJobMapper.updateById(scheduleJob);
    }

    /**
     * 删除job.
     *
     * @param scheduleJobId jobId
     * @throws SchedulerException
     */
    public void delete(Long scheduleJobId) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobMapper.deleteById(scheduleJobId);
    }

    /**
     * 运行一次
     *
     * @param scheduleJobId jobId
     * @throws SchedulerException
     */
    public void runOnce(Long scheduleJobId) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    /**
     * 暂停job
     *
     * @param scheduleJobId jobId
     * @throws SchedulerException
     */
    public void pauseJob(Long scheduleJobId) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduleJob.setStatus(String.valueOf(TRIGGER_STATE_ENUM.PAUSED.getCode()));
        scheduleJobMapper.updateById(scheduleJob);
    }

    /**
     * 恢复job
     *
     * @param scheduleJobId
     * @throws SchedulerException
     */
    public void resumeJob(Long scheduleJobId) throws SchedulerException {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduleJob.setStatus(String.valueOf(TRIGGER_STATE_ENUM.NORMAL.getCode()));
        scheduleJobMapper.updateById(scheduleJob);
    }

    /**
     * 获取job信息
     *
     * @param scheduleJobId
     * @return
     */
    public ScheduleJobVo get(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        return scheduleJob.getTargetObject(ScheduleJobVo.class);
    }

    /**
     * 分页查询
     *
     * @param page 查询条件
     * @return 分页数据
     */
    public IPage<ScheduleJob> page(Page page) throws ServiceTipsException {

        IPage<ScheduleJob> pages = scheduleJobMapper.selectPage(page, null);
        try {
            for (ScheduleJob vo : pages.getRecords()) {
                JobKey jobKey = ScheduleUtils.getJobKey(vo.getJobName(), vo.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }
                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();
                vo.setJobTrigger(trigger.getKey().getName());

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

                vo.setStatus(TRIGGER_STATE_ENUM.valueOf(triggerState.name()).getCode().toString());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    vo.setCronExpression(cronExpression);
                }
            }
        } catch (SchedulerException e) {
            throw new ServiceTipsException("获取job状态异常");
        }

        return pages;
    }


    /**
     * 获取运行中的job列表
     *
     * @return
     */
    @Deprecated
    public List<ScheduleJobVo> queryExecutingJobList() throws ServiceTipsException {

        // 存放结果集
        List<ScheduleJobVo> jobList = new ArrayList<>();

        // 获取scheduler中的JobGroupName
        try {
            for (String group : scheduler.getJobGroupNames()) {
                // 获取JobKey 循环遍历JobKey
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(group))) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    ScheduleJob scheduleJob = (ScheduleJob) jobDataMap.get(ScheduleJobVo.JOB_PARAM_KEY);
                    ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
                    BeanConverter.convert(scheduleJobVo, scheduleJob);
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggers.iterator().next();
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    scheduleJobVo.setJobTrigger(trigger.getKey().getName());
                    scheduleJobVo.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduleJobVo.setCronExpression(cronExpression);
                    }
                    // 获取正常运行的任务列表
                    if ("NORMAL".equals(triggerState.name())) {
                        jobList.add(scheduleJobVo);
                    }
                }
            }
        } catch (SchedulerException e) {
            throw
                    new ServiceTipsException("获取执行中的任务出错");
        }

        return jobList;

    }
}
