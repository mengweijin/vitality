package com.github.mengweijin.vitality.framework.jdbc;

import jakarta.validation.constraints.NotNull;
import org.dromara.hutool.core.text.StrUtil;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
public class BeanPropertyRowMapper<T> extends org.springframework.jdbc.core.BeanPropertyRowMapper<T> {

    @Override
    protected String lowerCaseName(@NotNull String name) {
        return super.lowerCaseName(StrUtil.replace(name, "_", ""));
    }
}
