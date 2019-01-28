package pers.goetboy.quartz.constant;

/**
 * job日志状态.
 * job是否成功执行完毕
 * 如果job执行过程中没有异常抛出，那么这个job就算是成功执行，反之亦然。
 *
 * @author:goetb
 * @date 2019 /01 /10
 **/
public enum SCHEDULE_JOB_LOG_STATE {
    /**
     * 执行成功
     */
    ok("1"),
    /**
     * 执行异常
     */
    error("0");
    private String value;

    SCHEDULE_JOB_LOG_STATE(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
