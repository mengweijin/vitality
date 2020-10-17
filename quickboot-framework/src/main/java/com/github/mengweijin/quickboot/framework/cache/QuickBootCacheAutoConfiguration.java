package com.github.mengweijin.quickboot.framework.cache;

import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.time.Duration;

/**
 * @author Meng Wei Jin
 * @description EnableCaching 开启缓存
 **/
@Configuration
@EnableCaching
public class QuickBootCacheAutoConfiguration {

    public static final String DEFAULT_CACHE_ALIAS = "defaultCache";

    /**
     * EhCache缓存管理器
     * 手动放入/取出缓存：
     * Cache<Serializable, Serializable> cache = ehCacheManager.getCache(DEFAULT_CACHE_ALIAS, Serializable.class, Serializable.class);
     * 放入：cache.put("a", "b");
     * 取出：cache.get("a");
     *
    * @return
    */
    @Bean
    @ConditionalOnMissingBean
    public CacheManager ehCacheManager() {
        // EhCacheManager管理缓存
        return CacheManagerBuilder.newCacheManagerBuilder()
                // 设置一个默认缓存配置
                .withCache(DEFAULT_CACHE_ALIAS, defaultCacheConfiguration())
                // 硬盘持久化地址
                //.with(CacheManagerBuilder.persistence(Const.JAVA_TMP_PATH))
                // 初始化CacheManager 传入true初始化；或者通过cacheManager.init();方法初始化
                .build(true);
    }

    /**
     * 默认缓存配置属性
     *
     * @return
     */
    private CacheConfiguration<Serializable, Serializable> defaultCacheConfiguration() {
        return CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 缓存数据K和V的数值类型，在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                Serializable.class,
                // V：需要实现Serializable接口
                Serializable.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                        // 设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
                        .heap(1000L, EntryUnit.ENTRIES)
                        // 设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰，数值大小必须小于磁盘配置的大小
                        //.offheap(50L, MemoryUnit.MB)
                        // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用
                        //.disk(100L, MemoryUnit.MB, false)
        )
                // 数据最大存活时间
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10L)))
                // 数据最大空闲时间
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(10L)))
                .build();
    }

}
