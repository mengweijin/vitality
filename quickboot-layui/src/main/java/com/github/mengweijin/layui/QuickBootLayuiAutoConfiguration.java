package com.github.mengweijin.layui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/9/11
 */
@Configuration
public class QuickBootLayuiAutoConfiguration {

    @Bean
    public QuickBootLayuiWebMvcConfigurer quickBootLayuiWebMvcConfigurer() {
        return new QuickBootLayuiWebMvcConfigurer();
    }
}
