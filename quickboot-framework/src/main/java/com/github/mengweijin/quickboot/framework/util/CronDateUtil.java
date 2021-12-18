package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.date.DateUtil;
import java.util.Date;

/**
 * @author Meng Wei Jin
 * @description
 **/
public class CronDateUtil {

    public static final String CRON = "ss mm HH dd MM ? yyyy";

    /**
     * 日期转cron表达式
     * @param date
     * @return
     */
    public static String formatCron(Date date){
        return DateUtil.format(date, CRON);
    }

    /**
     * cron表达式转Date
     *
     * @return
     */
    public static Date parseCron(String cron) {
        return DateUtil.parse(cron,CRON);
    }
}
