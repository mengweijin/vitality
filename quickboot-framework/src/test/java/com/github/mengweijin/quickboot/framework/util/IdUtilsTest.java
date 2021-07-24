package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class IdUtilsTest {

    @Test
    void getSnowflakeId() {
        String id = String.valueOf(IdUtils.getSnowflakeId());
        log.debug(id);
        assertTrue(ReUtil.isMatch("\\d+", id));
    }

    @Test
    void timestampId() {
        String id = String.valueOf(IdUtils.timestampId());
        log.debug(id);
        assertTrue(ReUtil.isMatch("\\d+", id));
    }
}