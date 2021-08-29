package com.github.mengweijin.quickboot.framework.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

/**
 * @author mengweijin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CacheExpire {

    /**
     * 缓存过期时间
     * 如果同时配置了 cron 和 expire > 0, 优先使用 expire 规则。
     */
    long expire() default 0;

    /**
     * 仅当采用expire字段设置过期时间时生效
     */
    ChronoUnit chronoUnit() default ChronoUnit.MINUTES;

    /**
     * 利用 cron设置过期时间
     */
    String cron() default "";
}
