package pers.goetboy.quartz.constant;

/**
 * job在调度中的执行状态.
 * 此枚举是 {@link org.quartz.Trigger.TriggerState} 的代码和中文语义说明
 * @author:goetboy,
 * @date 2018 /12 /26
 * @see org.quartz.Trigger.TriggerState
 **/
public enum TRIGGER_STATE_ENUM {
    BLOCKED(4, "阻塞"),
    COMPLETE(2, "完成"),
    ERROR(3, "出错"),
    NONE(-1, "不存在"),
    NORMAL(0, "正常"),
    PAUSED(1, "暂停");
    private Integer code;
    private String value;

    TRIGGER_STATE_ENUM(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
