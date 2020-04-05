package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.date.DatePattern;
import com.mengweijin.mwjwork.framework.orika.converter.LocalDateTimeToStringConverter;
import com.mengweijin.mwjwork.framework.orika.converter.LocalDateToStringConverter;
import com.mengweijin.mwjwork.framework.orika.converter.LocalTimeToStringConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.UtilityResolver;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryBuilderConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 */
@Component
public class BaseOrikaMapperFactoryBuilderConfigurator implements OrikaMapperFactoryBuilderConfigurer {

    /**
     * DateToStringConverter, 只能自定义时使用, 不然可能会有问题
     * classMapBuilder.fieldMap("startDate", "startDate").converter(DatePattern.NORM_DATE_PATTERN).add();
     * @param mapperFactoryBuilder
     */
    @Override
    public void configure(DefaultMapperFactory.MapperFactoryBuilder<?, ?> mapperFactoryBuilder) {
        ConverterFactory converterFactory = UtilityResolver.getDefaultConverterFactory();
        converterFactory.registerConverter(new LocalDateToStringConverter());
        converterFactory.registerConverter(new LocalTimeToStringConverter());
        converterFactory.registerConverter(new LocalDateTimeToStringConverter());
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_MS_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MS_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
        converterFactory.registerConverter(DatePattern.NORM_DATE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
        converterFactory.registerConverter(DatePattern.CHINESE_DATE_PATTERN, new DateToStringConverter(DatePattern.CHINESE_DATE_PATTERN));
        mapperFactoryBuilder.converterFactory(converterFactory);
    }
}
