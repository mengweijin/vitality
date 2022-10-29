package com.github.mengweijin.quickboot.cache;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.events.CacheEventListenerConfiguration;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.time.Duration;

/**
 * 原理说明：
 *
 * echahe2.x与springboot整合时，springcache已经提供了EhCacheCacheManager（org.springframework.cache.ehcache）
 * 做为@Cacheable的CacheManager（org.springframework.cache），因此只要提供一个EhCacheCacheManager即可
 *
 * echahe3.x与springboot整合时，因为EhCacheCacheManager中的CacheManager还是net.sf.ehcache.CacheManager
 * （也就是ecache2.x），所以必须借助JCacheCacheManager（org.springframework.cache.jcache）实现@Cacheable缓存。
 * 注意：JCache(JSR-107)是一种标准规范，在springboot中需要引入javax.cache.cache-api.jar包。
 *
 * ehcache和jcache结合参考：https://www.ehcache.org/documentation/3.8/107.html
 *
 * @author mengweijin
 * @date 2022/10/29
 */
@EnableCaching
@Configuration
public class CacheAutoConfiguration {

    @Bean("ehcacheManager")
    public CacheManager ehcacheManager() {
        // 从 jcache 中拿到 CacheManager
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        // 创建一个缓存名为 CacheConst.NAME_7_DAY 的缓存(核心是Eh107Configuration.fromEhcacheCacheConfiguration)
        cacheManager.createCache(CacheConst.NAME_7_DAY, defaultCacheConfiguration(Duration.ofDays(7L)));
        cacheManager.createCache(CacheConst.NAME_1_DAY, defaultCacheConfiguration(Duration.ofDays(1L)));
        cacheManager.createCache(CacheConst.NAME_10_MINUTES, defaultCacheConfiguration(Duration.ofMinutes(10L)));
        cacheManager.createCache(CacheConst.NAME_1_MINUTES, defaultCacheConfiguration(Duration.ofMinutes(1L)));
        return cacheManager;
    }

    /**
     * 默认缓存配置属性
     */
    private javax.cache.configuration.Configuration<String, Object> defaultCacheConfiguration(Duration duration) {
        return Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        // 缓存数据K和V的数值类型，在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                        String.class,
                        Object.class,
                        ResourcePoolsBuilder
                            // 设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到 offheap 中
                            .heap(10000L)
                            // 设置堆外内存大小(直接内存) 超出 offheap 的大小会淘汰规则被淘汰，数值大小必须小于磁盘配置的大小
                            .offheap(50, MemoryUnit.MB)
                            // 配置磁盘持久化储存大小(硬盘存储)，用来持久化到磁盘, 这里设置为false不启用
                            //.disk(100, MemoryUnit.MB, false)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(duration))
                // 数据最大存活时间 TTL
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(duration))
                // 缓存监听器
                .withService(cacheEventListenerConfiguration())
                .build()
        );
    }

    private CacheEventListenerConfiguration<?> cacheEventListenerConfiguration() {
        return CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(
                        new CustomCacheEventListener(), EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED, EventType.CREATED, EventType.UPDATED)
                .asynchronous()
                .unordered()
                .build();
    }
}
