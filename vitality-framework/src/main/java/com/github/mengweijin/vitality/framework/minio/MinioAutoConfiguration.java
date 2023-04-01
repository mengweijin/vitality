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
@Configuration
@EnableConfigurationProperties({MinioProperties.class})
public class MinioAutoConfiguration {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioService minioService() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                .build();
        return new MinioService(minioClient);
    }
}
