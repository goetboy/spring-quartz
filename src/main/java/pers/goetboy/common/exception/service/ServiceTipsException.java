package pers.goetboy.common.exception.service;

/**
 * 直接返回信息的业务异常
 * 业务层消息弹出异常
 *
 * @author goetb
 */
public class ServiceTipsException extends BaseServiceException {
    public ServiceTipsException() {
        super();
    }

    public ServiceTipsException(Throwable e) {
        super(e);
    }

    public ServiceTipsException(String message) {
        super(message);
    }

    public ServiceTipsException(Integer code, String message) {
        super(code, message);
    }

    public ServiceTipsException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ServiceTipsException(String message, Throwable cause,
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
