package pers.goetboy.quartz.model;

/**
 * Job执行resul对象
 *
 * @author:goetboy;
 * @date 2018 /12 /17
 * @see pers.goetboy.quartz.job.jobfactory.JobFactory
 **/
public class Result {
    public final static Integer ERROR = -1;
    public final static Integer SUCCESS = 0;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 信息
     */
    private String msg;

    public Result() {

    }

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}