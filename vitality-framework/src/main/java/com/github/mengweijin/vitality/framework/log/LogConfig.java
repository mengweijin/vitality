package com.github.mengweijin.vitality.framework.log;

import com.github.mengweijin.vitality.framework.log.aspect.LogOperationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@Configuration
public class LogConfig {

    @Bean
    public LogOperationAspect logOperationAspect() {
        return new LogOperationAspect();
    }

}
