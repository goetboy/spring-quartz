package pers.goetboy.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 数据源切换中心.
 * 数据操作时sprng会调用此类，将选定的数据源注入jdbc对象中
 * @author:goetboy;
 * @date 2018 /12 /17
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = DynamicDataHelper.getDataSource();
        return dataSource;
    }
}