package pers.goetboy.quartz.model.entity;

import pers.goetboy.common.bean.BeanConverter;
import pers.goetboy.common.Pageable;
import pers.goetboy.common.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志类
 *
 * @author:goetboy;
 * @date 2018 /12 /29
 **/
@Table(name = "schedule_job_log")
public class ScheduleJobLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_job_log_id")
    private Integer scheduleJobLogId;
    /**
     * 任务名
     */
    @Column(name = "job_name")
    private String jobName;
    /**
     * 任务id
     */
    @Column(name = "schedule_job_id")
    private Integer scheduleJobId;
    /**
     * 开始时间
     */
    @Column(name = "start_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @Column(name = "end_Time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime;
    /**
     * 信息
     */
    @Column(name = "info")
    private String info;
    /**
     * 状态
     */
    @Column(name = "state")
    private String state;

    public Integer getScheduleJobLogId() {
        return scheduleJobLogId;
    }

    public void setScheduleJobLogId(Integer scheduleJobLogId) {
        this.scheduleJobLogId = scheduleJobLogId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Integer scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 提取 {@link ScheduleJob 中 需要的字段至本对象} 目前提取的字段有
     * scheduleJobId 与jobName
     *
     * @param scheduleJob
     * @see ScheduleJob
     */
    public void setScheduleJob(ScheduleJob scheduleJob) {
        this.setScheduleJobId(scheduleJob.getScheduleJobId());
        this.setJobName(scheduleJob.getJobName());
        this.setStartTime(new Date());
    }

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getTargetObject(Class<T> clazz) throws BaseException {
        try {
            T t = clazz.newInstance();
            return BeanConverter.convert(t, this);
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage());
        }
    }
}
