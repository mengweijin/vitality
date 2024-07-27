package com.github.mengweijin.framework.mvc;

import com.github.mengweijin.framework.VitalityProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private VitalityProperties vitalityProperties;

    /**
     * 跨域
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        if (vitalityProperties.isCors()) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(errorPage404);
        });
    }

    /**
     * debug 模式不缓存静态资源
     */
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        if (vitalityProperties.isDebug()) {
            registry
                    // 设置哪些页面的静态资源不缓存，比如：[ "/**/index.html", "/" ]。
                    // 不要配置为 "/**"，会使某些页面 404 无法加载，比如 Knife4j 的 doc.html。
                    .addResourceHandler("/")
                    .addResourceLocations("classpath:/static/")
                    .setCacheControl(CacheControl.noStore());
        }
    }

}
