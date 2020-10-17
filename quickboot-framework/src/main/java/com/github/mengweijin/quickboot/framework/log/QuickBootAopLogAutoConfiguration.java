package com.github.mengweijin.quickboot.framework.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuickBootAopLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LogAop logAop() {
        return new LogAop();
    }
}
