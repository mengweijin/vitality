package com.github.mengweijin.quickboot.framework.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * LocalDate to String converter
 * @author mengweijin
 */
public class DateToLocalDateConverter extends BidirectionalConverter<Date, LocalDate> {

    private final ZoneId zoneId;

    public DateToLocalDateConverter() {
        this(ZoneId.systemDefault());
    }

    public DateToLocalDateConverter(final ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public LocalDate convertTo(Date source, Type<LocalDate> destinationType, MappingContext mappingContext) {
        return source.toInstant().atZone(zoneId).toLocalDate();
    }

    @Override
    public Date convertFrom(LocalDate source, Type<Date> destinationType, MappingContext mappingContext) {
        return Date.from(source.atStartOfDay(zoneId).toInstant());
    }
}
