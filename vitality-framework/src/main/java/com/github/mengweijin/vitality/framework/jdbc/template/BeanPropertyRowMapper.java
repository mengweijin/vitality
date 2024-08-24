package com.github.mengweijin.vitality.framework.jdbc.template;

import org.dromara.hutool.core.text.StrUtil;

/**
 * @author mengweijin
 * @since 2022/10/29
 */
@SuppressWarnings({"unused"})
public class BeanPropertyRowMapper<T> extends org.springframework.jdbc.core.BeanPropertyRowMapper<T> {

    @Override
    protected String lowerCaseName(String name) {
        return super.lowerCaseName(StrUtil.replace(name, "_", ""));
    }
}
