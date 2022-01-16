package com.github.mengweijin.quickboot.auth.client;

import com.github.mengweijin.quickboot.auth.client.filter.AuthClientProperties;
import com.github.mengweijin.quickboot.auth.client.filter.ClientTokenVerifyFilter;
import com.github.mengweijin.quickboot.auth.client.processor.QuickBootAuthClientBeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mengweijin
 * @date 2022/1/9
 */
@EnableConfigurationProperties(AuthClientProperties.class)
@Configuration
public class QuickBootAuthClientAutoConfiguration {

    @Autowired
    private ClientTokenVerifyFilter clientTokenVerifyFilter;

    /**
     * 添加 @Configuration(proxyBeanMethods = false) 或者把方法改为 static 以解决下面的问题。
     * Cannot enhance @Configuration bean definition 'com.github.mengweijin.quickboot.auth.client.QuickBootAuthClientAutoConfiguration' since its singleton instance has been created too early.
     * The typical cause is a non-static @Bean method with a BeanDefinitionRegistryPostProcessor return type: Consider declaring such methods as 'static'.
     */
    @Bean
    public static QuickBootAuthClientBeanDefinitionRegistryPostProcessor quickBootAuthDataBeanDefinitionRegistryPostProcessor(){
        return new QuickBootAuthClientBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<ClientTokenVerifyFilter> clientTokenVerifyFilterRegistrationBean() {
        FilterRegistrationBean<ClientTokenVerifyFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(clientTokenVerifyFilter);
        registration.addUrlPatterns("/*");
        registration.setName("clientTokenVerifyFilterRegistrationBean");
        registration.setOrder(1);
        return registration;
    }
}
