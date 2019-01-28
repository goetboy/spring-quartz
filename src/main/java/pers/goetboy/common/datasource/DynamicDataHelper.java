package pers.goetboy.common.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态数据源切换类.
 * 如需切换数据源可通过此类set数据源beanId
 *
 * @author:goetboy;
 * @date 2019 /01 /03
 * @see DynamicDataSource
 **/
public class DynamicDataHelper {
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DATASOURCE = "dataSource";
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataHelper.class);
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getDataSource() {
        // 如果没有指定数据源，使用默认数据源
        if (CONTEXT_HOLDER.get() == null) {
            setDataSource(DynamicDataHelper.DEFAULT_DATASOURCE);
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 设置线程访问数据源 (benanId)
     *
     * @param dataSource
     */
    public static void setDataSource(String dataSource) {
        logger.info("切换至数据源：{}", dataSource);
        CONTEXT_HOLDER.set(dataSource);
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
