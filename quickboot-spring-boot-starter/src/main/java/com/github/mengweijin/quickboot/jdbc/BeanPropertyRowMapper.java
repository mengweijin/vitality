package com.github.mengweijin.quickboot.jdbc;

import cn.hutool.core.util.StrUtil;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
public class BeanPropertyRowMapper<T> extends org.springframework.jdbc.core.BeanPropertyRowMapper<T> {

    @Override
    protected String lowerCaseName(String name) {
        return super.lowerCaseName(StrUtil.replace(name, "_", ""));
    }
}
