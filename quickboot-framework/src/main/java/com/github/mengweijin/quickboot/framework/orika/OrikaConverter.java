package com.github.mengweijin.quickboot.framework.orika;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
public enum OrikaConverter {
    LocalDateTimeToString_NORM_DATETIME_MS,
    LocalDateTimeToString_UTC,

    DateToString_NORM_DATETIME,
    DateToString_UTC_SIMPLE,
    DateToString_UTC,
    DateToString_UTC_MS,
    DateToString_NORM_DATE,
    DateToString_NORM_TIME;

    public String getId(){
        return this.name();
    }
}
