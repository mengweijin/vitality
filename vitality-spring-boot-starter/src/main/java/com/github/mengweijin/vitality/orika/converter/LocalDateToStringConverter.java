package com.github.mengweijin.vitality.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * LocalDate to String converter
 * @author mengweijin
 */
public class LocalDateToStringConverter extends BidirectionalConverter<LocalDate, String> {

    private final DateTimeFormatter dateTimeFormatter;

    public LocalDateToStringConverter(final DateTimeFormatter dateTimeFormatter){
        this.dateTimeFormatter = dateTimeFormatter;
    }
    @Override
    public String convertTo(LocalDate source, Type<String> destinationType, MappingContext mappingContext) {
        return source.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public LocalDate convertFrom(String source, Type<LocalDate> destinationType, MappingContext mappingContext) {
        return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
