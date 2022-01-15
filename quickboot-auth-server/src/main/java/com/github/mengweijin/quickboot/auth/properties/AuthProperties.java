package com.github.mengweijin.quickboot.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@ConfigurationProperties(prefix = "quickboot.auth")
@Data
@Validated
public class AuthProperties {

    private Login login = new Login();

    private Token token = new Token();

    @Data
    public static class Login {
        /**
         * 最大登录失败次数
         */
        protected int maxFailureTimes = 5;

        /**
         * 达到最大登录失败次数后，多长时间可以再次登录。单位：分钟
         */
        protected int expire = 30;
    }


    @Data
    public static class Token {
        /**
         * 令牌自定义标识。
         * Authorization 可以跨域
         */
        private String header = "Authorization";

        /**
         * 令牌秘钥
         */
        protected String secret = "abcdefghijklmnopqrstuvwxyz";

        /**
         * 令牌有效期，单位：秒（默认30分钟）
         */
        protected int expire = 30 * 60;
    }
}
