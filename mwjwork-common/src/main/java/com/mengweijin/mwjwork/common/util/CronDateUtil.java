package com.mengweijin.mwjwork.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
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
        return DateFormatUtils.format(date, CRON);
    }

    /**
     * cron表达式转Date
     *
     * @return
     */
    public static Date parseCron(String cron) throws ParseException {
        return DateUtils.parseDate(cron, CronDateUtil.CRON);
    }
}
