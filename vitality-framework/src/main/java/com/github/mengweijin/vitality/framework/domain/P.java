package com.github.mengweijin.vitality.framework.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
public final class P {

    private P(){
    }

    public static ObjectMapper objectMapper() {
        return SpringUtil.getBean(ObjectMapper.class);
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
