package pers.goetboy.quartz.common.util;

import pers.goetboy.quartz.services.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 项目执行时初始化job
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 * @see ScheduleJobService#initScheduleJob()
 **/
@Component
public class ScheduleJobInit {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobInit.class);

    /**
     * 定时任务service
     */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 项目启动时初始化
     * 容器加载 servlet 时执行此方法 将数据库中配置的job装载
     */
    @PostConstruct
    public void init() {
        logger.info("init job...");
        scheduleJobService.initScheduleJob();
        logger.info("end");

    }

}
