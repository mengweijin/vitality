package com.github.mengweijin.vita.framework.repeatable;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author mengweijin
 * @since 2022/1/16
 */
@SuppressWarnings({"unused"})
@Configuration
public class RepeatableConfig {

    @Bean
    public FilterRegistrationBean<RepeatableFilter> repeatableFilter() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

}
