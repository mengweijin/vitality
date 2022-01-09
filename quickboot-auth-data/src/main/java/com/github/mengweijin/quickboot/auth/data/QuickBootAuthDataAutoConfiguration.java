package com.github.mengweijin.quickboot.auth.data;

import com.github.mengweijin.quickboot.auth.data.processor.QuickBootAuthDataBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/1/9
 */
@Configuration
public class QuickBootAuthDataAutoConfiguration {

    @Bean
    public QuickBootAuthDataBeanDefinitionRegistryPostProcessor quickBootAuthDataBeanDefinitionRegistryPostProcessor(){
        return new QuickBootAuthDataBeanDefinitionRegistryPostProcessor();
    }
}
