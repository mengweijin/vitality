package com.github.mengweijin.quickboot.mybatis.sping.jdbc;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
 * When use the JdbcTemplate to query data, you can set a ColumnMapRowMapper,
 * this class can convert underline to camel case when you use JdbcTemplate to return a Map data list.
 *
 * @author mengweijin
 */
public class CamelColumnMapRowMapper extends ColumnMapRowMapper {

    /**
     * column name under line to camel
     *
     * @param columnName columnName
     * @return columnName
     */
    @Override
    protected String getColumnKey(String columnName) {
        return StringUtils.underlineToCamel(super.getColumnKey(columnName));
    }
}
