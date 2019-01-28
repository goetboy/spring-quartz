package pers.goetboy.quartz.controller;

import pers.goetboy.common.PageGrid;
import pers.goetboy.common.exception.service.ServiceTipsException;
import pers.goetboy.quartz.common.exception.SchedulerException;
import pers.goetboy.quartz.job.JOB_ENUM;
import pers.goetboy.quartz.model.entity.ScheduleJob;
import pers.goetboy.quartz.model.vo.ScheduleJobVo;
import pers.goetboy.quartz.services.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * job业务控制器.
 * 页面对job的相关操作接口在此类抛出
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
@CrossOrigin
@RestController
@RequestMapping(value = "/batch")
public class ScheduleJobController extends AbstractController {
    /**
     * job 业务处理服务类
     */
    private final ScheduleJobService scheduleJobService;

    @Autowired
    public ScheduleJobController(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }


    /**
     * 获取job信息.
     * 通过scheduleJobId 获取job的详细信息
     *
     * @param scheduleJobId jobId
     * @return job详细信息
     */
    @GetMapping(value = "get")
    public ResponseEntity<ScheduleJobVo> getScheduleJob(Long scheduleJobId) {
        ScheduleJobVo scheduleJob = scheduleJobService.get(scheduleJobId);

        return success(scheduleJob);
    }

    /**
     * 删除job
     * 通过scheduleJobId 删除job
     *
     * @param scheduleJobId jobId
     * @return null
     */
    @PostMapping(value = "/delete")
    public ResponseEntity deleteScheduleJob(@RequestBody Long scheduleJobId) throws SchedulerException {

        scheduleJobService.delete(scheduleJobId);

        return success();
    }

    /**
     * 运行一次
     *
     * @return null
     */
    @PostMapping(value = "/runOnc")
    public ResponseEntity runOnceScheduleJob(@RequestBody Long scheduleJobId) throws SchedulerException {
        scheduleJobService.runOnce(scheduleJobId);
        return success();
    }

    /**
     * 暂停job.
     * 暂停执行指定job
     *
     * @param scheduleJobId jobId
     * @return null
     */
    @PostMapping(value = "/pause")
    public ResponseEntity pauseScheduleJob(@RequestBody Long scheduleJobId) throws SchedulerException {
        scheduleJobService.pauseJob(scheduleJobId);
        return success();
    }

    /**
     * 恢复job.
     * 恢复执行指定job
     *
     * @param scheduleJobId jobId
     * @return null
     */
    @PostMapping(value = "/resume")
    public ResponseEntity resumeScheduleJob(@RequestBody Long scheduleJobId) throws SchedulerException {
        scheduleJobService.resumeJob(scheduleJobId);
        return success();
    }

    /**
     * 保存任务.
     * 新增job并保存至数据库.
     * job名称不能与库中已有job重复
     *
     * @param scheduleJobVo job信息
     * @return null
     */
    @PostMapping(value = "/save")
    public ResponseEntity saveScheduleJob(@RequestBody ScheduleJobVo scheduleJobVo) {

        if (scheduleJobVo.getScheduleJobId() == null) {
            scheduleJobService.insert(scheduleJobVo);
        } else {
            scheduleJobService.update(scheduleJobVo);
        }
        return success();
    }


    /**
     * 任务列表页.
     * 获取数据库中已经创建的job任务列表.
     * 获取到的任务信息已经包含在内存中的状态.
     *
     * @return 任务列表分页数据
     * @see pers.goetboy.quartz.constant.TRIGGER_STATE_ENUM job状态枚举
     * @see PageGrid 分页
     */
    @GetMapping(value = "/list")
    public ResponseEntity<PageGrid<ScheduleJob>> listScheduleJob(ScheduleJobVo scheduleJobVo) throws ServiceTipsException {

        PageGrid<ScheduleJob> pageGrid = scheduleJobService.page(scheduleJobVo);
        return success(pageGrid);
    }

    /**
     * 执行类列表.
     * 业务系统中已经开发的job执行类,添加job要执行的
     *
     * @return 执行类列表
     */
    @GetMapping(value = "/job/list")
    public ResponseEntity<List<Map<String, String>>> listJobClass() {
        return success(JOB_ENUM.map());
    }

    /**
     * 当前正在执行任务列表页
     *
     * @return 正在执行的任务列表
     */
    @GetMapping(value = "/executing/list")
    @Deprecated
    public ResponseEntity<List<ScheduleJobVo>> listExecutingScheduleJob() throws ServiceTipsException {
        List<ScheduleJobVo> executingJobList = scheduleJobService.queryExecutingJobList();
        return success(executingJobList);
    }
}
