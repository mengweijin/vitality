package com.github.mengweijin.framework.jdbc.template;

import org.dromara.hutool.core.text.StrUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author mengweijin
 * @since 2022/10/29
 */
public class BeanPropertyRowMapper<T> extends org.springframework.jdbc.core.BeanPropertyRowMapper<T> {

    @NotNull
    @Override
    protected String lowerCaseName(@NotNull String name) {
        return super.lowerCaseName(StrUtil.replace(name, "_", ""));
    }
}
