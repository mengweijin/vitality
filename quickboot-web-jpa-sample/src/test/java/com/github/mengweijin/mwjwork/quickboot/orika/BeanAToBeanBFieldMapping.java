package com.github.mengweijin.mwjwork.quickboot.orika;

import cn.hutool.core.date.DatePattern;
import com.github.mengweijin.quickboot.framework.orika.OrikaFieldMapping;
import ma.glasnost.orika.metadata.ClassMapBuilder;

public class BeanAToBeanBFieldMapping extends OrikaFieldMapping<BeanA, BeanB> {

    @Override
    public void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
        classMapBuilder
                .field("nameA", "nameB")
                .fieldMap("normDate", "normDate").converter(DatePattern.NORM_DATE_PATTERN).add()
                .fieldMap("normDateTimeMinute", "normDateTimeMinute").converter(DatePattern.NORM_DATETIME_MINUTE_PATTERN).add()
                .fieldMap("normDateTimeMs", "normDateTimeMs").converter(DatePattern.NORM_DATETIME_MS_PATTERN).add()
                .fieldMap("normDateTime", "normDateTime").converter(DatePattern.NORM_DATETIME_PATTERN).add()
                .fieldMap("chineseDate", "chineseDate").converter(DatePattern.CHINESE_DATE_PATTERN).add()
        ;
    }
}
