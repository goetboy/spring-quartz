package pers.goetboy.common.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pers.goetboy.common.exception.BaseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器异常处理类。
 * 将控制器抛出的异常封装，
 * 目前异常编号只有 1001 可以根据情况将异常进行异常编号, 编号异常需要将编号放入{@link BaseException#resultcode} 属性中
 * 主要处理以下几种异常
 * 扩展{@link BaseException}异常
 * 通过 {@link BaseException#getMessage()}返回异常消息
 * {@link Exception}异常
 * 统一异常消息为 "操作异常"
 *
 * @author goetb
 */
@ControllerAdvice
public class ExceptionAdvice {
    private final String DEBFULT_ERROR_MESSAGE = "操作异常";
    private final Integer DEBFULT_ERROR_CODE = 1001;
    @Autowired
    Environment environment;
    private Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 统一异常处理
     *
     * @param ex       异常信息
     * @param request  请求对象
     * @param response 返回对象
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("========接口产生异常========");
        logger.error("异常信息:", ex);
        logger.error("===========================");
        String msg = "";
        Integer status = null;
        if (ex instanceof BaseException) {
            if (((BaseException) ex).getResultcode() != null) {
                status = ((BaseException) ex).getResultcode();
            }
            if (status == null) {
                status = DEBFULT_ERROR_CODE;
            }
            msg = environment.getProperty(status.toString());

            if (msg == null || msg.trim().isEmpty()) {
                msg = DEBFULT_ERROR_MESSAGE;
            }
        } else if (ex instanceof Exception) {

            msg = DEBFULT_ERROR_MESSAGE;
        }
        return ResponseEntity.status(status).body(msg);
    }
}
