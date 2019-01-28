package pers.goetboy.common.exception;

/**
 * 基础异常
 *
 * @author goetb
 * @author:goetboy;
 * @date 2018 /12 /24
 **/
public class BaseException extends RuntimeException {
    Integer resultcode;
    String resultMessage;

    public BaseException() {
        super();
    }

    public BaseException(Throwable e) {
        super(e);
    }

    public BaseException(String message) {
        super(message);
        this.resultMessage = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.resultcode = code;
        this.resultMessage = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.resultMessage = message;
    }

    protected BaseException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultMessage = message;
    }

    public Integer getResultcode() {
        return resultcode;
    }

    public String getResultMessage() {
        return resultMessage;
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
