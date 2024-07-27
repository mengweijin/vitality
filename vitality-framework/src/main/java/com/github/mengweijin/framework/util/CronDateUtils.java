package com.github.mengweijin.framework.util;

import org.dromara.hutool.core.date.DateUtil;
import org.springframework.scheduling.support.CronExpression;
import java.util.Date;

/**
 * @author Meng Wei Jin
 **/
public class CronDateUtils {

    public static final String CRON = "ss mm HH dd MM ? yyyy";

    /**
     * 日期转cron表达式
     *
     * @param date date
     * @return String
     */
    public static String formatCron(Date date) {
        return DateUtil.format(date, CRON);
    }

    /**
     * cron表达式转Date
     *
     * @return Date
     */
    public static Date parseCron(String cron) {
        return DateUtil.parse(cron, CRON);
    }

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

}
