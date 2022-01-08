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
}
