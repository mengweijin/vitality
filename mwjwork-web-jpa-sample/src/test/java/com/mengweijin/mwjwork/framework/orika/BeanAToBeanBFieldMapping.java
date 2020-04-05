package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.date.DatePattern;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.stereotype.Component;

@Component
public class BeanAToBeanBFieldMapping extends BaseOrikaMapperFactoryConfigurator<BeanA, BeanB> {

    @Override
    void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
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
