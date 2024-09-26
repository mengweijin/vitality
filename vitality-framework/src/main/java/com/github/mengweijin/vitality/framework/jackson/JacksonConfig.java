package com.github.mengweijin.vitality.framework.jackson;

import com.github.mengweijin.vitality.framework.domain.P;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @author Meng Wei Jin
 **/
@Slf4j
@Configuration
@AllArgsConstructor
@SuppressWarnings({"unused"})
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.modules(P.javaTimeModule());
            builder.timeZone(TimeZone.getDefault());
            log.info("init jackson config completed.");
        };
    }

}
