package pers.goetboy.quartz.job;

import pers.goetboy.common.datasource.BaseDao;
import pers.goetboy.common.SpringContextUtil;
import pers.goetboy.quartz.common.exception.SchedulerException;
import pers.goetboy.quartz.model.Result;
import pers.goetboy.quartz.model.entity.ScheduleJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务监控任务基类.
 * 此类的派升类均应为检测数据库中数据异常的调度任务
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
public abstract class AccessMonitorBaseJob {

    private static final Logger logger = LoggerFactory.getLogger(AccessMonitorBaseJob.class);
    @Autowired
    BaseDao baseDao;

    /**
     * 重写此方法
     *
     * @param scheduleJob
     */
    public abstract Result execute(ScheduleJob scheduleJob) throws SchedulerException;

    /**
     * 初始化调度任务.
     * 由于调度任务是通过反射执行，类中某些属性不能初始化 可以通过调用此方法初始化
     * 如果派生类有新的属性需要处理，或者其它处理需求 可以重写此方法
     * 目前仅有一个属性{@link #baseDao}需要初始化
     */
    public void initJob() {
        if (baseDao == null) {
            baseDao = SpringContextUtil.getBean("jdbcTemplate");
        }
    }

}
