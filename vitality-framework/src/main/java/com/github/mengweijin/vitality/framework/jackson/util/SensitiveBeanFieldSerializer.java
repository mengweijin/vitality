package com.github.mengweijin.vitality.framework.jackson.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏
 *
 * @author mengweijin
 */
@Slf4j
public class SensitiveBeanFieldSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(JacksonBeanSensitiveUtils.HIDE_VALUE);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.equals(String.class, property.getType().getRawClass()) && StrUtil.containsAnyIgnoreCase(property.getName(), JacksonBeanSensitiveUtils.SENSITIVE_KEY)) {
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
