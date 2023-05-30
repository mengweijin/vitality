package com.github.mengweijin.vitality.framework.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@ConfigurationProperties(prefix = "minio")
@Data
@Validated
public class MinioProperties {

    private String url;

    private String username;

    private String password;

    private String bucket;
}
