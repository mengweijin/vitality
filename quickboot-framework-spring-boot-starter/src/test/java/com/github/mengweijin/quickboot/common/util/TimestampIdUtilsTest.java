package com.github.mengweijin.quickboot.common.util;

import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class TimestampIdUtilsTest {

    @Test
    void timestampId() {
        String id = String.valueOf(TimestampIdUtils.timestampId());
        log.debug(id);
        assertTrue(ReUtil.isMatch("\\d+", id));
    }
}