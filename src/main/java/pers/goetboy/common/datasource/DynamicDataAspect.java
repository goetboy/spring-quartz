package pers.goetboy.common.datasource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 动态数据源切换切面，项目业务无需求，暂时没有启用
 * @author:goetboy;
 * @date 2019 /01 /02
 **/
//@Component
//@Aspect
public class DynamicDataAspect {
    Logger log = LoggerFactory.getLogger("【数据源切换器】");

    @After("execution(* com.ex.*.services..*.*(..))")
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        DynamicDataHelper.clearDataSource();
        log.debug("数据源已移除！");

    }

    @Before("execution(* com.ex.*.services..*.*(..))")
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        if (method.isAnnotationPresent(DataSource.class)) {
            DataSource dataSource = method.getAnnotation(DataSource.class);
            DynamicDataHelper.setDataSource(dataSource.value());
        }
    }
}
