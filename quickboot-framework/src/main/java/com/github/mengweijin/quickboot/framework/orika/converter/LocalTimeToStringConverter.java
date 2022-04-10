package com.github.mengweijin.quickboot.framework.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalTime to String converter
 * @author mengweijin
 */
public class LocalTimeToStringConverter extends BidirectionalConverter<LocalTime, String> {

    private final DateTimeFormatter dateTimeFormatter;

    public LocalTimeToStringConverter(final DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }
    @Override
    public String convertTo(LocalTime source, Type<String> destinationType, MappingContext mappingContext) {
        return source.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Override
    public LocalTime convertFrom(String source, Type<LocalTime> destinationType, MappingContext mappingContext) {
        return LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
