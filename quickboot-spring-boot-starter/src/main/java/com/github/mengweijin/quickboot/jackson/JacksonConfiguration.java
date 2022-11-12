package com.github.mengweijin.quickboot.jackson;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Meng Wei Jin
 **/
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Map<Class<?>, JsonSerializer<?>> map = new HashMap<>(2);
        map.put(Long.class, LongToStringSerializer.INSTANCE);
        map.put(Long.TYPE, LongToStringSerializer.INSTANCE);
        return builder -> builder.serializersByType(map);
    }
}
