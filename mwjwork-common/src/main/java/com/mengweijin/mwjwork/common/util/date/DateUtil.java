package com.mengweijin.mwjwork.common.util.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Meng WEi Jin
 * @description
 **/
public class DateUtil extends DateUtils {


    /**
     * 获取当前Date型日期
     *
     * @return Date 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static String getNowYear() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getNowMonth() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.MONTH));
    }

    /**
     * cron表达式转Date
     *
     * @return
     */
    public static Date parseCron(String cron) throws ParseException {
        return DateUtils.parseDate(cron, DateFormatUtil.CRON);
    }

}
