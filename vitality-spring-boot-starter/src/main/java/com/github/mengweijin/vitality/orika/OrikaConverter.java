package com.github.mengweijin.vitality.orika;

/**
 * Refer date pattern to {@link com.github.mengweijin.quickboot.orika.configurer.QuickBootOrikaMapperFactoryConfigurer}
 * and {@link cn.hutool.core.date.DatePattern}
 * @author mengweijin
 * @date 2022/4/3
 */
public enum OrikaConverter {
    LOCAL_DATE_TIME_NORM_DATETIME_MS,
    LOCAL_DATE_TIME_UTC,

    DATE_NORM_DATETIME,
    DATE_UTC_SIMPLE,
    DATE_UTC,
    DATE_UTC_MS,
    DATE_NORM_DATE,
    DATE_NORM_TIME;

    public String getId(){
        return this.name();
    }
}
