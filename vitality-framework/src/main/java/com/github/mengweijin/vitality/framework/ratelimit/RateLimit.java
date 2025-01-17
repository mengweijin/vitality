package com.github.mengweijin.vitality.framework.ratelimit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 限流
 *
 * @author mengweijin
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 限流单位时间。单位：秒
     */
    int duration() default 30;

    /**
     * 限流单位时间内限制的请求个数。
     */
    int max() default 100;

    /**
     * 限流策略
     */
    ERateLimitStrategy strategy() default ERateLimitStrategy.API;

    /**
     * 提示消息
     */
    String message() default "Too many requests. Please try later.";

}
