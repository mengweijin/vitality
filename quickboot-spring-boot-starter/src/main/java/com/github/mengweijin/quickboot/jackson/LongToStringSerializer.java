package com.github.mengweijin.quickboot.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/**
 * @author mengweijin
 * @date 2022/11/12
 */
@JacksonStdImpl
public class LongToStringSerializer extends StdSerializer<Long> {
    private static final long JS_MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long JS_MIN_SAFE_INTEGER = -9007199254740991L;
    public final static LongToStringSerializer INSTANCE = new LongToStringSerializer();

    public LongToStringSerializer() { super(Long.class, false); }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if(value <= JS_MAX_SAFE_INTEGER && value >= JS_MIN_SAFE_INTEGER){
            gen.writeNumber(value);
        }else{
            gen.writeString(value.toString());
        }
    }
}
