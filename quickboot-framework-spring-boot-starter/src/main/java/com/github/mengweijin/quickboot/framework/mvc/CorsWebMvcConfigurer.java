package com.github.mengweijin.quickboot.framework.mvc;

import com.github.mengweijin.quickboot.framework.QuickBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 **/
public class CorsWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private QuickBootProperties quickBootProperties;

    /**
     * 跨域
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if(quickBootProperties.isCors()) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }
}
