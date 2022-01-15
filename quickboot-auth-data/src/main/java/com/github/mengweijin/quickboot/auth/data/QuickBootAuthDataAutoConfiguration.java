package com.github.mengweijin.quickboot.auth.data;

import com.github.mengweijin.quickboot.auth.data.processor.QuickBootAuthDataBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/1/9
 */
@Configuration(proxyBeanMethods = false)
public class QuickBootAuthDataAutoConfiguration {

    /**
     * 添加 @Configuration(proxyBeanMethods = false) 或者把方法改为 static 以解决下面的问题。
     * Cannot enhance @Configuration bean definition 'com.github.mengweijin.quickboot.auth.data.QuickBootAuthDataAutoConfiguration' since its singleton instance has been created too early.
     * The typical cause is a non-static @Bean method with a BeanDefinitionRegistryPostProcessor return type: Consider declaring such methods as 'static'.
     */
    @Bean
    public QuickBootAuthDataBeanDefinitionRegistryPostProcessor quickBootAuthDataBeanDefinitionRegistryPostProcessor(){
        return new QuickBootAuthDataBeanDefinitionRegistryPostProcessor();
    }
}
