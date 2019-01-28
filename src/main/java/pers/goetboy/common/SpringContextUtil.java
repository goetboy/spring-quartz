package pers.goetboy.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring上下文工具类
 *
 * @author:goetboy;
 * @date 2018 /12 /20
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;


    /**
     * 通过bean类型获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {

        return applicationContext.getBean(clazz);

    }

    /**
     * 通过beanId获取bean
     *
     * @param beanId
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanId) {
        return (T) applicationContext.getBean(beanId);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }


}
