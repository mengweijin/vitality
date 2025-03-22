package com.github.mengweijin.vita.framework.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.github.mengweijin.vita.framework.jackson.translation.modifier.TranslationBeanSerializerModifier;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 */
@Component
@AllArgsConstructor
public class JacksonInitialization {

    private ObjectMapper objectMapper;

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        // 重新设置 Jackson 的 Bean 序列化修改器
        SerializerFactory serializerFactory = objectMapper.getSerializerFactory().withSerializerModifier(new TranslationBeanSerializerModifier());
        objectMapper.setSerializerFactory(serializerFactory);
    }

}
