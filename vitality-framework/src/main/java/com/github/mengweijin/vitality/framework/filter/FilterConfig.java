package com.github.mengweijin.vitality.framework.filter;

import com.github.mengweijin.vitality.framework.VitalityProperties;
import com.github.mengweijin.vitality.framework.filter.repeatable.RepeatableFilter;
import com.github.mengweijin.vitality.framework.filter.xss.XssFilter;
import com.github.mengweijin.vitality.framework.filter.xss.XssProperties;
import com.github.mengweijin.vitality.framework.util.Const;
import jakarta.servlet.DispatcherType;
import org.jsoup.Jsoup;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
@Configuration
public class FilterConfig {

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<RepeatableFilter> repeatableFilter() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jsoup.class)
    public FilterRegistrationBean<XssFilter> xssFilter(VitalityProperties vitalityProperties) {
        XssProperties xssProperties = vitalityProperties.getXss();
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put(XssFilter.EXCLUDES_NAME, String.join(Const.COMMA, xssProperties.getExcludes()));
        initParameters.put(XssFilter.ENABLED_NAME, String.valueOf(xssProperties.getEnabled()));
        registration.setInitParameters(initParameters);
        return registration;
    }
}
