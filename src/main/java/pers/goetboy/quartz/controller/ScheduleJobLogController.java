package pers.goetboy.quartz.controller;

import pers.goetboy.common.PageGrid;
import pers.goetboy.quartz.model.entity.ScheduleJobLog;
import pers.goetboy.quartz.model.vo.ScheduleJobLogVo;
import pers.goetboy.quartz.services.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 调度日志业务controller
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
@CrossOrigin
@RestController
@RequestMapping(value = "/batch/log")
public class ScheduleJobLogController extends AbstractController {
    /**
     * job service
     */
    private final ScheduleJobLogService scheduleJobLogService;

    @Autowired
    public ScheduleJobLogController(ScheduleJobLogService scheduleJobLogService) {
        this.scheduleJobLogService = scheduleJobLogService;
    }

    /**
     * 当前正在执行任务列表页
     *
     * @return 正在执行任务列表
     */
    @GetMapping(value = "/list")
    public ResponseEntity<PageGrid<ScheduleJobLog>> list(ScheduleJobLogVo scheduleJobLog) {
        PageGrid<ScheduleJobLog> scheduleJobLogPageGrid = scheduleJobLogService.page(scheduleJobLog);
        return success(scheduleJobLogPageGrid);
    }
}
