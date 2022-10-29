package com.github.mengweijin.quickboot.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Configuration
public class ResponseBodyAdviceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DefaultResponseBodyAdvice defaultResponseBodyAdvice() {
        return new DefaultResponseBodyAdvice();
    }

}
