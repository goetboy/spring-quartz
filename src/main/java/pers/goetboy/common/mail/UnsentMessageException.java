package pers.goetboy.common.mail;

import pers.goetboy.common.exception.service.BaseServiceException;

/**
 * 消息未发送异常
 *
 * @author:goetb
 * @date 2019 /01 /23
 **/
public class UnsentMessageException extends BaseServiceException {
    private static final long serialVersionUID = 1L;

    public UnsentMessageException() {
    }

    public UnsentMessageException(Throwable e) {
        super(e);
    }

    public UnsentMessageException(String message) {
        super(message);
    }

    public UnsentMessageException(Integer code, String message) {
        super(code, message);
    }

    public UnsentMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsentMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
