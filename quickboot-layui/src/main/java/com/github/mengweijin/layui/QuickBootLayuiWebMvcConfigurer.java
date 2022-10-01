package com.github.mengweijin.layui;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <a href="http://localhost:8080/quickboot/admin">http://localhost:8080/quickboot/admin</a>
 * <a href="http://localhost:8080/quickboot/home">http://localhost:8080/quickboot/home</a>
 *
 * @author mengweijin
 * @date 2022/9/11
 */
public class QuickBootLayuiWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/quickboot/admin").setViewName("quickboot/admin/index");
        registry.addViewController("/quickboot/home").setViewName("quickboot/home/index");
    }
}
