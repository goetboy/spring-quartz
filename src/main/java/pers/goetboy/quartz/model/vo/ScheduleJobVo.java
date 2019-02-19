package pers.goetboy.quartz.model.vo;



import pers.goetboy.common.bean.BeanConverter;

import java.util.Date;

/**
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
public class ScheduleJobVo  {

    /**
     * 任务调度的参数key
     */
    public static final String JOB_PARAM_KEY = "jobParam";
    /**
     * 任务id
     */
    private Integer scheduleJobId;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务别名
     */
    private String aliasName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 触发器
     */
    private String jobTrigger;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 任务运行时间表达式
     */
    private String cronExpression;

    /**
     * 是否异步
     */
    private Boolean isSync;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 任务执行url
     */
    private String url;
    /**
     * 邮件
     */
    private String email;
    private String param;
    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getTargetObject(Class<T> clazz) {
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

    public Boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSync() {
        return isSync;
    }

    public void setSync(Boolean sync) {
        isSync = sync;
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
        return "ScheduleJobVo{" +
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
                "} " + super.toString();
    }
}
