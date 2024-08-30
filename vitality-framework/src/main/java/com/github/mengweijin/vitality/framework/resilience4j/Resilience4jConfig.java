package com.github.mengweijin.vitality.framework.resilience4j;

import io.github.resilience4j.common.ratelimiter.configuration.RateLimiterConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 方法上使用：@RateLimiter(name = Resilience4jConfig.RATE_LIMITER_DEFAULT)
 *
 * {@link io.github.resilience4j.ratelimiter.annotation.RateLimiter}
 *
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@Configuration
public class Resilience4jConfig {

    public static final String RATE_LIMITER_DEFAULT = "DEFAULT";

    public static final String RATE_LIMITER_ONE_PER_SECOND = "ONE_PER_SECOND";

    /**
     * limit-for-period：一个“limit-refresh-period”期间允许的请求数
     * limit-refresh-period：指定“limit-for-period”将被重置的持续时间
     * timeout-duration：设置速率限制器允许后续请求的最大等待时间。
     * <p>
     * 默认参数：
     * private Duration timeoutDuration = Duration.ofSeconds(5);
     * private Duration limitRefreshPeriod = Duration.ofNanos(500);
     * private int limitForPeriod = 50;
     * 每 500 纳秒限制通过 50 个请求，同时如果请求超过 5 秒没有响应，则认为请求超时。
     * </p>
     *
     * @return RateLimiterConfigCustomizer
     */
    @Bean
    public RateLimiterConfigCustomizer defaultRrateLimiterConfigCustomizer() {
        return RateLimiterConfigCustomizer.of(RATE_LIMITER_DEFAULT, builder -> {
        });
    }

    /**
     * 每 1 秒限制通过 1 个请求，同时如果请求超过 5 秒没有响应，则认为请求超时。
     *
     * @return RateLimiterConfigCustomizer
     */
    @Bean
    public RateLimiterConfigCustomizer onePerSencondRateLimiterConfigCustomizer() {
        return RateLimiterConfigCustomizer.of(RATE_LIMITER_ONE_PER_SECOND, builder -> {
            builder.limitRefreshPeriod(Duration.ofSeconds(1L)).limitForPeriod(1).timeoutDuration(Duration.ofSeconds(5));
        });
    }

}
