package com.github.mengweijin.framework.jdbc.template;

import jakarta.validation.constraints.NotNull;
import org.dromara.hutool.core.text.StrUtil;

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
