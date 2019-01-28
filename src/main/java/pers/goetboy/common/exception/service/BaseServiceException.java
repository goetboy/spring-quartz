package pers.goetboy.common.exception.service;

import pers.goetboy.common.exception.BaseException;

/**
 * 业务层异常
 *
 * @author goetb
 */
public class BaseServiceException extends BaseException {
    public BaseServiceException() {
        super();
    }


    public BaseServiceException(Throwable e) {
        super(e);
    }

    public BaseServiceException(String message) {
        super(message);
    }

    public BaseServiceException(Integer code, String message) {
        super(code, message);
    }

    public BaseServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BaseServiceException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        Throwable cause = super.getCause();
        if (cause == null || cause == this) {
            return super.toString();
        } else {
            return super.toString() + " [See nested exception: " + cause + "]";
        }
    }
}
