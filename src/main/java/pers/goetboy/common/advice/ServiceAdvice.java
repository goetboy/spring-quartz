package pers.goetboy.common.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * service日志切面
 *
 * @author goetb
 */
@Aspect
@Component
public class ServiceAdvice {
    private static final
    Logger logger = LoggerFactory.getLogger(ServiceAdvice.class);

    @Pointcut("execution(* pers.goetboy.*.services..*.*(..))")
    public void serviceAop() {
    }

    @Around("serviceAop()")
    public Object print(ProceedingJoinPoint point) throws Throwable {

        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().getName();
        logger.info("{}.{} start...", className, methodName);
        logger.info("param:{}", Arrays.toString(point.getArgs()));
        Object result = null;
        try {
            //before(point);
            result = point.proceed();
        } finally {
            //  afterReturning(point);
        }
        if (result != null) {
            logger.info("return:{{}}", result.toString());
        }

        logger.info("{}.{} end...", className, methodName);
        return result;
    }


}
