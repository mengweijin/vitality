package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.date.DatePattern;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@Configuration
public class OrikaConfig {

    /**
     * MapperFacade
     * @param mapperFactory mapperFactory
     * @return MapperFacade
     */
    @Bean
    public MapperFacade mapperFacade(MapperFactory mapperFactory) {
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new LocalDateToStringConverter());
        converterFactory.registerConverter(new LocalTimeToStringConverter());
        converterFactory.registerConverter(new LocalDateTimeToStringConverter());
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_MS_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_TIME_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.CHINESE_DATE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_MS_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_PATTERN));
        return mapperFactory.getMapperFacade();
    }
}
