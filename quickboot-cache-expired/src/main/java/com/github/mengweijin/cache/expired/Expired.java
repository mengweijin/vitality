package com.github.mengweijin.cache.expired;

import lombok.Data;

import java.time.temporal.ChronoUnit;

/**
 * @author mengweijin
 * @date 2022/1/2
 */
@Data
public class Expired {

    /**
     * 缓存过期时间
     * 如果同时配置了 cron 和 expire > 0, 优先使用 expire 规则。
     */
    private long expire;

    /**
     * 仅当采用expire字段设置过期时间时生效
     */
    private ChronoUnit chronoUnit;

    /**
     * 利用 cron设置过期时间
     */
    private String cron;

    public static Expired toExpired(CacheExpired cacheExpired) {
        Expired expired = new Expired();
        expired.setExpire(cacheExpired.expire());
        expired.setChronoUnit(cacheExpired.chronoUnit());
        expired.setCron(cacheExpired.cron());
        return expired;
    }
}
