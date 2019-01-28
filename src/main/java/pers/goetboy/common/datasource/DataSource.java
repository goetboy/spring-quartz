package pers.goetboy.common.datasource;

import java.lang.annotation.*;

/**
 * 动态数据源切换注解，将此类注解到class或者method上 可以自动切换数据源
 *
 * @author:goetboy;
 * @date 2019 /01 /02
 * @see DynamicDataAspect
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String defaultDataSource = "dataSource";

    String value() default defaultDataSource;
}
