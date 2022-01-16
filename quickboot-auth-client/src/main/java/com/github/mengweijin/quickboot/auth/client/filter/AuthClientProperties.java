package com.github.mengweijin.quickboot.auth.client.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@ConfigurationProperties(prefix = "quickboot.auth.client")
@Data
@Validated
public class AuthClientProperties {
    /**
     * 令牌自定义标识。
     * Authorization 可以跨域
     */
    private String header = "Authorization";

    private String loginUrl = "http://localhost:8080/login";

    private String verifyUrl = "http://localhost:8080/token/verify";

}
