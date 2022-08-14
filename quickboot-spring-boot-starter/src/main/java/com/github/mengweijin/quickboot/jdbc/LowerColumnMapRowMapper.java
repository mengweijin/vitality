package com.github.mengweijin.quickboot.jdbc;

import org.springframework.jdbc.core.ColumnMapRowMapper;

import java.util.Locale;

/**
 * When use the JdbcTemplate to query data, you can set a ColumnMapRowMapper,
 * this class can convert underline to camel case when you use JdbcTemplate to return a Map data list.
 *
 * @author mengweijin
 */
public class LowerColumnMapRowMapper extends ColumnMapRowMapper {

    /**
     * column name under line to lower case
     *
     * @param columnName columnName
     * @return columnName
     */
    @Override
    protected String getColumnKey(String columnName) {
        return columnName.toLowerCase(Locale.ROOT);
    }
}
