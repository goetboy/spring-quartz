package pers.goetboy.common.exception;

/**
 * @author:goetboy;
 * @date 2018 /12 /27
 **/
public class CommonsAssistantException extends BaseException {
    public CommonsAssistantException() {
        super();
    }

    public CommonsAssistantException(Throwable e) {
        super(e);
    }

    public CommonsAssistantException(String message) {
        super(message);
    }

    public CommonsAssistantException(Integer code, String message) {
        super(code, message);
    }

    public CommonsAssistantException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CommonsAssistantException(String message, Throwable cause,
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
        }    }

}
