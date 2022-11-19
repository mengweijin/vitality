package com.github.mengweijin.vitality.domain;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.exception.QuickBootException;

/**
 * @author mengweijin
 * @date 2022/5/17
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
            throw new QuickBootException(e);
        }
    }
}
