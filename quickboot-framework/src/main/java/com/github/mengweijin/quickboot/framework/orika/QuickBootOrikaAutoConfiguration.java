package com.github.mengweijin.quickboot.framework.orika;

import cn.hutool.core.date.DatePattern;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateTimeToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalTimeToStringConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@Configuration
public class QuickBootOrikaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DefaultMapperFactory.MapperFactoryBuilder<?, ?> orikaMapperFactoryBuilder() {
        DefaultMapperFactory.Builder orikaMapperFactoryBuilder = new DefaultMapperFactory.Builder();
        return orikaMapperFactoryBuilder;
    }

    /**
     * DateToStringConverter, 只能自定义时使用, 不然可能会有问题
     * classMapBuilder.fieldMap("startDate", "startDate").converter(DatePattern.NORM_DATE_PATTERN).add();
     */
    @Bean
    @ConditionalOnMissingBean
    public MapperFactory orikaMapperFactory(DefaultMapperFactory.MapperFactoryBuilder<?, ?> orikaMapperFactoryBuilder) {
        DefaultMapperFactory mapperFactory = orikaMapperFactoryBuilder.build();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new LocalDateToStringConverter());
        converterFactory.registerConverter(new LocalTimeToStringConverter());
        converterFactory.registerConverter(new LocalDateTimeToStringConverter());
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_MS_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MS_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
        converterFactory.registerConverter(DatePattern.CHINESE_DATE_PATTERN, new DateToStringConverter(DatePattern.CHINESE_DATE_PATTERN));
        return mapperFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public MapperFacade orikaMapperFacade(MapperFactory orikaMapperFactory) {
        MapperFacade orikaMapperFacade = orikaMapperFactory.getMapperFacade();
        return orikaMapperFacade;
    }
}
