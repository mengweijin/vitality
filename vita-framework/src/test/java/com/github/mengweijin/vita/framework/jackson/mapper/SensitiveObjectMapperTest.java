package com.github.mengweijin.vita.framework.jackson.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2024/10/10
 */
class SensitiveObjectMapperTest {

    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("username", "admin");
        map.put("password", "123456");
    }

    @Test
    void writeValueAsString() {
        String value = SensitiveObjectMapper.writeValueAsString(new UserTest("123456", map));
        Assertions.assertEquals("{\"password\":\"********\",\"map\":{\"password\":\"********\",\"username\":\"admin\"}}", value);
    }

    @Data
    @AllArgsConstructor
    static class UserTest {
        private String password;

        private Map<String, String> map;


    }
}