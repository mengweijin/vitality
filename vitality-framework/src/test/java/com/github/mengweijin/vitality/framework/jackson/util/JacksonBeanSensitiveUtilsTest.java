package com.github.mengweijin.vitality.framework.jackson.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @since 2024/10/10
 */
class JacksonBeanSensitiveUtilsTest {

    @Test
    void writeValueAsString() {
        String value = JacksonBeanSensitiveUtils.writeValueAsString(new UserTest("123456"));
        Assertions.assertEquals("{\"password\":\"****************\"}", value);
    }

    @Data
    @AllArgsConstructor
    static class UserTest {
        private String password;
    }
}