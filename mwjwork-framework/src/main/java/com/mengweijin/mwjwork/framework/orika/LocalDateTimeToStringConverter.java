package com.mengweijin.mwjwork.framework.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime to String converter
 * @author mengweijin
 */
public class LocalDateTimeToStringConverter extends BidirectionalConverter<LocalDateTime, String> {

    @Override
    public String convertTo(LocalDateTime source, Type<String> destinationType, MappingContext mappingContext) {
        return source.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public LocalDateTime convertFrom(String source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
