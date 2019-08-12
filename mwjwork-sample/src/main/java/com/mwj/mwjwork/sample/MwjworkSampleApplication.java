package com.mwj.mwjwork.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多模块项目要配置scanBasePackages
 */
@SpringBootApplication(scanBasePackages = {"com.mwj.mwjwork"})
public class MwjworkSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MwjworkSampleApplication.class, args);
    }

}
