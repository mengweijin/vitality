package com.mengweijin.mwjwork.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多模块项目要配置scanBasePackages
 */
@SpringBootApplication(scanBasePackages = {"com.mengweijin"})
public class MwjworkSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MwjworkSampleApplication.class, args);
    }

}
