package com.mengweijin.mwjwork.mybatis.sping.jdbc;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.jdbc.core.ColumnMapRowMapper;

/**
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
