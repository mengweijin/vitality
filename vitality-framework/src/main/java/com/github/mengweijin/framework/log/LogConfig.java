package com.github.mengweijin.framework.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@Configuration
public class LogConfig {

    @Bean
    public LogOperationAspect logAspect() {
        return new LogOperationAspect(aopLog -> {});
    }

}
