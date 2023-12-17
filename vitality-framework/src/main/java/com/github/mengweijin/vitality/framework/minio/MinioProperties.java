package com.github.mengweijin.vitality.framework.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@ConfigurationProperties(prefix = "minio")
@ConditionalOnClass({MinioClient.class})
@Data
@Validated
public class MinioProperties {

    private String url;

    private String username;

    private String password;

    private String bucket;
}
