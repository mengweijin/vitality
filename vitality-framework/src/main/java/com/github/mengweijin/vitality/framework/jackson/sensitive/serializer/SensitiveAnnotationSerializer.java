package com.github.mengweijin.vitality.framework.jackson.sensitive.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.github.mengweijin.vitality.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vitality.framework.jackson.sensitive.Sensitive;
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
public class SensitiveAnnotationSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private Sensitive sensitive;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ESensitiveStrategy strategy = sensitive.strategy();
        if(strategy == null) {
            log.warn("Desensitization strategies don't exist! Default hide all value!");
            gen.writeString(StrUtil.hide(value, 0, StrUtil.length(value)));
        } else {
            gen.writeString(strategy.desensitize().apply(value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitive sensitive = property.getAnnotation(Sensitive.class);
        if (Objects.nonNull(sensitive) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.sensitive = sensitive;
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
