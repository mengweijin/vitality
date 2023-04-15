package com.github.mengweijin.vitality.framework.minio;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinioConfig {

    @Bean
    public MinioService minioService() {
        return new MinioService();
    }
}
