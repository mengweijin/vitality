package com.github.mengweijin.vitality.aop;

import com.github.mengweijin.vitality.aop.log.LogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Configuration
public class AopConfig {

    @Bean
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect(sysLog -> {});
    }

}
