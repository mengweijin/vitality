package com.github.mengweijin.vitality.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;

/**
 * @author mengweijin
 * @since 2022/11/12
 */
@JacksonStdImpl
public class BigNumberSerializer extends NumberSerializer {

    private static final long JS_MAX_SAFE_INTEGER = 9007199254740991L;

    private static final long JS_MIN_SAFE_INTEGER = -9007199254740991L;

    public final static BigNumberSerializer INSTANCE = new BigNumberSerializer();

    public BigNumberSerializer() {
        super(Number.class);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value.longValue() <= JS_MAX_SAFE_INTEGER && value.longValue() >= JS_MIN_SAFE_INTEGER) {
            super.serialize(value, gen, provider);
        } else {
            gen.writeString(value.toString());
        }
    }
}
