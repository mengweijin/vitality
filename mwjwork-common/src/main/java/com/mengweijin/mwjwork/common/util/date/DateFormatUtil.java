package com.mengweijin.mwjwork.common.util.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author Meng Wei Jin
 * @description
 **/
public class DateFormatUtil extends DateFormatUtils {

    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_PATH = "yyyy/MM/dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String CRON = "ss mm HH dd MM ? yyyy";

    /**
     * 日期转cron表达式
     * @param date
     * @return
     */
    public static String formatCron(Date date){
        return DateFormatUtils.format(date, CRON);
    }
}
