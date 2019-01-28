package pers.goetboy.quartz.common.exception;

import pers.goetboy.common.exception.BaseException;

/**
 * 数据库数据异常
 *
 * @author:goetboy;
 * @date 2018 /12 /27
 **/
public class DataBaseAccessException extends BaseException {

    /**
     * Instantiates a new ScheduleException.
     *
     * @param e the e
     */
    public DataBaseAccessException(Throwable e) {
        super(e);
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public DataBaseAccessException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param code    the code
     * @param message the message
     */
    public DataBaseAccessException(Integer code, String message) {
        super(code, message);
    }

    @Override
    public String toString() {
        Throwable cause = super.getCause();
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }    }
}
