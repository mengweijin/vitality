package com.github.mengweijin.vita.framework.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class P {

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
