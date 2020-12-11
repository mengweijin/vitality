package com.github.mengweijin.quickboot.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多模块项目要配置scanBasePackages
 * @author mengweijin
 */
@SpringBootApplication
public class QuickBootSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickBootSampleApplication.class, args);
    }

}
