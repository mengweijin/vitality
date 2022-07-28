package com.github.mengweijin.quickboot.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * LocalDate to String converter
 * @author mengweijin
 */
public class DateToLocalDateTimeConverter extends BidirectionalConverter<Date, LocalDateTime> {

    private final ZoneId zoneId;

    public DateToLocalDateTimeConverter() {
        this(ZoneId.systemDefault());
    }

    public DateToLocalDateTimeConverter(final ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public LocalDateTime convertTo(Date source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
        return LocalDateTime.ofInstant(source.toInstant(), zoneId);
    }

    @Override
    public Date convertFrom(LocalDateTime source, Type<Date> destinationType, MappingContext mappingContext) {
        return Date.from(source.atZone(zoneId).toInstant());
    }
}
