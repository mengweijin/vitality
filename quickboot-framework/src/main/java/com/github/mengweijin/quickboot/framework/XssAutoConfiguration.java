package com.github.mengweijin.quickboot.framework;

import com.github.mengweijin.quickboot.framework.filter.xss.XssFilter;
import com.github.mengweijin.quickboot.framework.filter.xss.XssProperties;
import com.github.mengweijin.quickboot.framework.util.Const;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
@Configuration
@ConditionalOnClass(Jsoup.class)
public class XssAutoConfiguration {

    @Autowired
    private QuickBootProperties quickBootProperties;

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<XssFilter> xssFilter() {
        XssProperties xssProperties = quickBootProperties.getXss();
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put(XssFilter.EXCLUDES, String.join(Const.COMMA, xssProperties.getExcludes()));
        initParameters.put(XssFilter.ENABLED, String.valueOf(xssProperties.getEnabled()));
        registration.setInitParameters(initParameters);
        return registration;
    }
}
