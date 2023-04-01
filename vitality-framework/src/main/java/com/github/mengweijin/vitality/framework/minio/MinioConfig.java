package com.github.mengweijin.vitality.framework.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
// @ConditionalOnClass({ MinioClient.class })
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                .build();
    }
    @Bean
    public MinioService minioService() {
        return new MinioService();
    }
}
