package com.github.mengweijin.vita.framework.mvc;

import com.github.mengweijin.vita.framework.VitaProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 **/
@Configuration
@AllArgsConstructor
@SuppressWarnings({"unused"})
public class WebMvcConfig implements WebMvcConfigurer {

    private VitaProperties vitaProperties;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(errorPage404);
        });
    }

    /**
     * 允许跨域
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**");
    }

    /**
     * 重定向：registry.addViewController("/").setViewName("/vitality/index.html");
     * 转发：registry.addRedirectViewController("/", "/vitality/index.html");
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/vitality/index.html");
    }

}
