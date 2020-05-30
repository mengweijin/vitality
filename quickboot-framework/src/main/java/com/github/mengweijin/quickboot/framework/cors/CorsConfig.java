package com.github.mengweijin.quickboot.framework.cors;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * Cors configuration
 * @author mengweijin
 */
@ConditionalOnProperty(name = "quickboot.cors.enabled", matchIfMissing = true)
@EnableConfigurationProperties(CorsProperties.class)
@Configuration
@AllArgsConstructor
public class CorsConfig {

    /**
     * The configuration properties for Cors.
     */
    private final CorsProperties corsProperties;

    @ConditionalOnMissingBean
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // origin
        addAllowedOrigin(config);
        // HEADER
        addAllowedHeader(config);
        // Method
        addAllowedMethod(config);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 顺序很重要，设置在最前
        bean.setOrder(0);
        return bean;
    }

    /**
     * 设置允许的网站域名。如：http://localhost:8010，全允许则设为 *
     * @param config CorsConfiguration
     */
    private void addAllowedOrigin(CorsConfiguration config) {
        List<String> whiteList = corsProperties.getUrlWhiteList();
        if(CollectionUtils.isEmpty(whiteList)) {
            config.addAllowedOrigin("*");
        } else {
            whiteList.forEach(config::addAllowedOrigin);
        }
    }

    /**
     * 限制 HEADER, 可以自行更改，全允许则设为 *, 如果不配置默认为 *
     * @param config CorsConfiguration
     */
    private void addAllowedHeader(CorsConfiguration config) {
        List<String> whiteList = corsProperties.getHeaderWhiteList();
        if(CollectionUtils.isEmpty(whiteList)) {
            config.addAllowedHeader("*");
        } else {
            whiteList.forEach(config::addAllowedHeader);
        }
    }

    /**
     * 限制 Method, 可以自行更改，全允许则设为 *, 如果不配置默认为 *
     * @param config CorsConfiguration
     */
    private void addAllowedMethod(CorsConfiguration config) {
        List<String> whiteList = corsProperties.getMethodWhiteList();
        if(CollectionUtils.isEmpty(whiteList)) {
            config.addAllowedMethod("*");
        } else {
            whiteList.forEach(config::addAllowedMethod);
        }
    }
}
