package pers.goetboy;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pers.goetboy.common.datasource.BaseDao;
import pers.goetboy.common.datasource.DynamicDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类.
 * 在此处配置数据源和相关db操作类
 *
 * @author:goetboy;
 * @date 2019 /01 /21
 **/
@Configuration
@PropertySource({"classpath:jdbc.properties"})
@EnableTransactionManagement
public class DatasourceConfig {
    private final Environment environment;

    @Autowired
    public DatasourceConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * @return 默认源
     */
    @Bean
    public DriverManagerDataSource defaultDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        driverManagerDataSource.setUrl(environment.getProperty("jdbc.url"));
        driverManagerDataSource.setUsername(environment.getProperty("jdbc.username"));
        driverManagerDataSource.setPassword(environment.getProperty("jdbc.password"));
        return driverManagerDataSource;

    }
    /**
     * @return 作业源
     */
 /*   @Bean
    public DriverManagerDataSource jobDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        driverManagerDataSource.setUrl(environment.getProperty("jdbc.job1.url"));
        driverManagerDataSource.setUsername(environment.getProperty("jdbc.job1.username"));
        driverManagerDataSource.setPassword(environment.getProperty("jdbc.job1.password"));
        return driverManagerDataSource;
    }*/

    /**
     * @return 动态数据源
     */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource());
        Map<Object, Object> map = new HashMap<>(2);
        map.put("dataSource", defaultDataSource());
      //  map.put("job1", prlifeDataSource());
        dynamicDataSource.setTargetDataSources(map);
        return dynamicDataSource;
    }

    /**
     * sql session工厂。
     *
     * @return sqlSession 工厂
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 事务管理
     *
     * @return 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource());
        return dataSourceTransactionManager;
    }


    /**
     * jdbcTemlate
     *
     * @return jdbcTemlate
     */
    @Bean(name = "jdbcTemplate")
    public BaseDao jdbcTemplate() {
        BaseDao baseDao = new BaseDao();
        baseDao.setDataSource(dynamicDataSource());
        return baseDao;
    }


}
