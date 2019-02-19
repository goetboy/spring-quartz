package pers.goetboy.quartz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.goetboy.quartz.model.entity.ScheduleJobLog;
import pers.goetboy.quartz.services.ScheduleJobLogService;


/**
 * 调度日志业务controller
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
    public ResponseEntity<IPage<ScheduleJobLog>> list(Integer current, Integer size) {

        Page page = new Page(current, size);
        IPage<ScheduleJobLog> scheduleJobLogPageGrid = scheduleJobLogService.page(page);
        return success(scheduleJobLogPageGrid);
    }
}
