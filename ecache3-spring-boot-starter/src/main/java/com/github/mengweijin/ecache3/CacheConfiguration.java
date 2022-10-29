package com.github.mengweijin.ecache3;

import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.events.CacheEventListenerConfiguration;
import org.ehcache.event.EventType;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@EnableCaching
@Configuration
public class CacheConfiguration {

    /**
     * EhCache缓存管理器
     * @return
     */
    @Bean
    public PersistentCacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                // 设置一个默认缓存配置
                .withCache(CacheConst.NAME_DEFAULT, defaultCacheConfiguration(Duration.ofDays(7L)))
                .withCache(CacheConst.NAME_1_DAY, defaultCacheConfiguration(Duration.ofDays(1L)))
                .withCache(CacheConst.NAME_10_MINUTES, defaultCacheConfiguration(Duration.ofMinutes(10L)))
                .withCache(CacheConst.NAME_1_MINUTES, defaultCacheConfiguration(Duration.ofMinutes(1L)))
                // 硬盘持久化地址
                .with(CacheManagerBuilder.persistence(System.getProperty("java.io.tmpdir")))
                // 初始化CacheManager 必须传入true；或者通过cacheManager.init()初始化
                .build(true);
    }

    /**
     * 默认缓存配置属性
     */
    private org.ehcache.config.CacheConfiguration<Object, Object> defaultCacheConfiguration(Duration duration) {
        return CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 缓存数据K和V的数值类型，在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                Object.class,
                Object.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        // 设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                        .heap(1000L, EntryUnit.ENTRIES)
                        // 设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰，数值大小必须小于磁盘配置的大小
                        .offheap(100L, MemoryUnit.MB)
                        // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用
                        .disk(200L, MemoryUnit.MB, false)
        )
        // 数据最大存活时间
        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(duration))
        // 缓存监听器
        .withService(cacheEventListenerConfiguration())
        .build();
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
