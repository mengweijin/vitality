package com.github.mengweijin.quickboot.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Configuration
public class LogAutoConfiguration {

    @Bean
    @Profile({"!prod"})
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "quickboot.debug", havingValue = "true")
    public LogAspect logAspect() {
        return new LogAspect(appLog -> {});
    }

}
