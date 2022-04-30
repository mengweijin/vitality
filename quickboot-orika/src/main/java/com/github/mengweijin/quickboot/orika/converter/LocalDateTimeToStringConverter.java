package com.github.mengweijin.quickboot.orika.converter;

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

    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateTimeToStringConverter(final DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public String convertTo(LocalDateTime source, Type<String> destinationType, MappingContext mappingContext) {
        return source.format(dateTimeFormatter);
    }

    @Override
    public LocalDateTime convertFrom(String source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return LocalDateTime.parse(source, dateTimeFormatter);
    }
}
