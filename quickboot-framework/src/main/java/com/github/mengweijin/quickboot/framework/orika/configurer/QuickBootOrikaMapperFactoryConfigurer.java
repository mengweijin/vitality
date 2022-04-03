package com.github.mengweijin.quickboot.framework.orika.configurer;

import cn.hutool.core.date.DatePattern;
import com.github.mengweijin.quickboot.framework.orika.OrikaMapperFactoryConfigurer;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Component
public class QuickBootOrikaMapperFactoryConfigurer implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        // converterFactory.registerConverter(new LocalDateToStringConverter());
        // converterFactory.registerConverter(new LocalTimeToStringConverter());
        // converterFactory.registerConverter(new LocalDateTimeToStringConverter());
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_MS_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.NORM_TIME_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.CHINESE_DATE_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_MS_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN));
        converterFactory.registerConverter(new DateToStringConverter(DatePattern.UTC_PATTERN));
    }
}
