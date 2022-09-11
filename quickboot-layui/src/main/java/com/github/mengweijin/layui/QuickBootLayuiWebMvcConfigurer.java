package com.github.mengweijin.layui;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mengweijin
 * @date 2022/9/11
 */
public class QuickBootLayuiWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/quickboot").setViewName("quickboot/index");
    }
}
