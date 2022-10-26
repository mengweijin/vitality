package com.github.mengweijin.layui;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <a href="http://localhost:8080/quickboot/admin">http://localhost:8080/quickboot/admin</a>
 * <a href="http://localhost:8080/quickboot/home">http://localhost:8080/quickboot/home</a>
 *
 * @author mengweijin
 * @date 2022/9/11
 */
@Configuration
public class QuickBootLayuiAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/quickboot/admin").setViewName("quickboot/admin");
        registry.addViewController("/quickboot/home").setViewName("quickboot/home");
        registry.addViewController("/quickboot/demo/img").setViewName("quickboot/demo/img");
        registry.addViewController("/quickboot/generator-side").setViewName("quickboot/generator-side");
        registry.addViewController("/quickboot/flowable-side").setViewName("quickboot/flowable-side");
    }

}
