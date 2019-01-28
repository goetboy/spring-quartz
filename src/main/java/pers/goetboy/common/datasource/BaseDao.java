package pers.goetboy.common.datasource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 基础dao.
 * 继承了{@link JdbcTemplate}
 *
 * @author:goetboy;
 * @date 2018 /12 /17
 **/

public class BaseDao<T> extends JdbcTemplate {
}
