package com.github.mengweijin.vitality.framework.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@Configuration
public class LogConfig {

    @Bean
    public LogOperationAspect logAspect() {
        return new LogOperationAspect(aopLog -> {});
    }

}
