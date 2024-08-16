package com.github.mengweijin.framework.cache;

import org.dromara.hutool.swing.captcha.ICaptcha;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.events.CacheEventListenerConfiguration;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

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
 * {@link <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache">Spring Cache Documents</a>}
 *
 * 1、使用注解
 * KEY_EXPRESSION 为 @Cacheable 中的 key 值，默认使用 SPEL 表达式，若要拼接普通文本，需要用单引号包裹起来。
 *
 * Example 1: @Cacheable(value = CacheName.USER, key = CacheConst.KEY_CLASS_METHOD, unless = "#result?.size() == 0")
 * Example 2: @Cacheable(value = CacheName.USER, key = CacheConst.KEY_CLASS + "+#username + 'zhangsan'", unless = "#result == null")
 *
 * 2、使用 {@link CacheFactory}
 *
 * @author mengweijin
 * @since 2022/10/29
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public JCacheManagerCustomizer jCacheManagerCustomizer() {
        return cacheManager -> {
            // 创建一个缓存名为 CacheName.DICT_LIST 的缓存(核心是Eh107Configuration.fromEhcacheCacheConfiguration)
            cacheManager.createCache(CacheName.DICT_LIST, config(Duration.ofDays(30L), String.class, List.class));
            cacheManager.createCache(CacheName.DICT_DATA_LABEL, config(Duration.ofDays(30L), String.class, String.class));
            cacheManager.createCache(CacheName.DEPT_ID_NAME, config(Duration.ofDays(30L), Long.class, String.class));
            cacheManager.createCache(CacheName.USER_ID_USERNAME, config(Duration.ofDays(30L), Long.class, String.class));
            cacheManager.createCache(CacheName.USER_ID_NICKNAME, config(Duration.ofDays(30L), Long.class, String.class));
            cacheManager.createCache(CacheName.CAPTCHA, config(Duration.ofMinutes(1L), String.class, ICaptcha.class));
            cacheManager.createCache(CacheName.REPEAT_SUBMIT, config(Duration.ofSeconds(10L), String.class, Long.class));
        };
    }

    /**
     * 默认缓存配置属性
     */
    public static <K, V> javax.cache.configuration.Configuration<K, V> config(Duration duration, Class<K> keyType, Class<V> valueType) {
        CacheConfigurationBuilder<K, V> builder = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 缓存数据K和V的数值类型，在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
                keyType,
                valueType,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    // 设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到 offheap 中
                    //.heap(30, MemoryUnit.MB) // 基于堆大小的缓存需要打开：--add-opens=java.base/java.util=ALL-UNNAMED
                    .heap(1000L)
                    // 设置堆外内存大小(直接内存) 超出 offheap 的大小会根据淘汰规则被淘汰，数值大小必须小于磁盘大小
                    //.offheap(10, MemoryUnit.MB)
        )
        // 缓存监听器
        .withService(cacheEventListener());

        if(duration != null) {
            // 数据最大存活时间 TTL
            builder.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(duration));
        }
        return Eh107Configuration.fromEhcacheCacheConfiguration(builder.build());
    }

    private static CacheEventListenerConfiguration<?> cacheEventListener() {
        return CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(
                        new CustomCacheEventListener(), EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED, EventType.CREATED, EventType.UPDATED)
                .asynchronous()
                .unordered()
                .build();
    }
}
