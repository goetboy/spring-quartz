package pers.goetboy.quartz.model.entity;

import pers.goetboy.common.bean.BeanConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {
    public static final String JOB_PARAM_KEY = "jobParam";
    /**
     * 任务id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_job_id")
    private Integer scheduleJobId;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 任务别名
     */
    @Column(name = "alias_name")
    private String aliasName;

    /**
     * 任务分组
     */
    @Column(name = "job_group")
    private String jobGroup;

    /**
     * 触发器
     */
    @Column(name = "job_Trigger")
    private String jobTrigger;

    /**
     * 任务状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 任务运行时间表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 是否异步
     */
    @Column(name = "is_sync")
    private Boolean isSync;
    /**
     * 任务描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify")
    private Date gmtModify;

    /**
     * 任务执行url
     */
    @Column(name = "url")
    private String url;
    /**
     * 邮件
     */
    @Column(name = "email")
    private String email;
    /**
     * 参数
     */
    @Column(name = "param")
    private String param;

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getTargetObject(Class<T> clazz) throws ClassCastException {
        try {
            T t = clazz.newInstance();
            return BeanConverter.convert(t, this);
        } catch (Exception e) {
            throw new ClassCastException(e.getMessage());
        }
    }

    public Integer getScheduleJobId() {
        return scheduleJobId;
    }

    public void setScheduleJobId(Integer scheduleJobId) {
        this.scheduleJobId = scheduleJobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ScheduleJob{" +
                "scheduleJobId=" + scheduleJobId +
                ", jobName='" + jobName + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", jobTrigger='" + jobTrigger + '\'' +
                ", status='" + status + '\'' +
                ", cronExpression='" + cronExpression + '\'' +
                ", isSync=" + isSync +
                ", description='" + description + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", url='" + url + '\'' +
                ", email='" + email + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
