package com.github.mengweijin.vita.framework.jackson.mapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.github.mengweijin.vita.framework.jackson.mapper.SensitiveObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * Bean 属性数据脱敏
 *
 * @author mengweijin
 */
@Slf4j
public class SensitiveBeanFieldSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(SensitiveObjectMapper.HIDE_VALUE);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.equals(String.class, property.getType().getRawClass()) && StrUtil.containsAnyIgnoreCase(property.getName(), SensitiveObjectMapper.SENSITIVE_KEY)) {
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
