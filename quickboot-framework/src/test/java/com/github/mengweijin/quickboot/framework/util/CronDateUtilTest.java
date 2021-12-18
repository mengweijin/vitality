package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2021/12/17
 */
class CronDateUtilTest {

    static String CRON = "34 37 18 17 12 ? 2021";

    @Test
    void formatCron() {
        String cron = CronDateUtil.formatCron(CronDateUtil.parseCron(CRON));
        Assertions.assertEquals(CRON, cron);
    }

    @Test
    void parseCron() {
        Date date = CronDateUtil.parseCron(CRON);
        Assertions.assertEquals("2021-12-17 18:37:34", DateUtil.formatDateTime(date));
    }
}