package pers.goetboy.quartz.model.vo;

import lombok.Data;
import pers.goetboy.common.bean.BeanConverter;
import pers.goetboy.common.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author:goetb
 * @date 2019 /01 /18
 **/
@Data
public class ScheduleJobLogVo {
    private Integer scheduleJobLogId;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 任务id
     */
    private Integer scheduleJobId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date endTime;
    private String info;
    private String state;


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
