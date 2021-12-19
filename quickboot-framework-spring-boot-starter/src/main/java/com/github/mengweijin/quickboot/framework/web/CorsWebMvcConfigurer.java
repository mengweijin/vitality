package com.github.mengweijin.quickboot.framework.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 * @description
 **/
public class CorsWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${quickboot.cors: false}")
    private boolean cors;

    /**
     * 跨域
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if(cors) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }
}
