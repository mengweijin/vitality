package com.github.mengweijin.vita.framework.cache;

import com.github.mengweijin.vita.framework.cache.listener.DefaultCacheEventListener;
import lombok.AllArgsConstructor;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.events.CacheEventListenerConfiguration;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * 原理说明：
 * <p>
 * echahe2.x与springboot整合时，springcache已经提供了EhCacheCacheManager（org.springframework.cache.ehcache）
 * 做为@Cacheable的CacheManager（org.springframework.cache），因此只要提供一个EhCacheCacheManager即可
 * <p>
 * echahe3.x与springboot整合时，因为EhCacheCacheManager中的CacheManager还是net.sf.ehcache.CacheManager
 * （也就是ecache2.x），所以必须借助JCacheCacheManager（org.springframework.cache.jcache）实现@Cacheable缓存。
 * 注意：JCache(JSR-107)是一种标准规范，在springboot中需要引入javax.cache.cache-api.jar包。
 * <p>
 * ehcache和jcache结合参考：https://www.ehcache.org/documentation/3.8/107.html
 * <p>
 * {@link <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache">Spring Cache Documents</a>}
 * <p>
 * 1、使用注解
 * KEY_EXPRESSION 为 @Cacheable 中的 key 值，默认使用 SPEL 表达式，若要拼接普通文本，需要用单引号包裹起来。
 * <p>
 * Example 1: @Cacheable(value = CacheNames.USER, key = CacheConst.KEY_CLASS_METHOD, unless = "#result?.size() == 0")
 * Example 2: @Cacheable(value = CacheNames.USER, key = CacheConst.KEY_CLASS + "+#username + 'zhangsan'", unless = "#result == null")
 * <p>
 * 2、使用 {@link CacheFactory}
 *
 * @author mengweijin
 * @since 2022/10/29
 */
@EnableCaching
@Configuration
@AllArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class CacheConfig {

    /**
     * 默认缓存配置属性
     */
    public static <K, V> javax.cache.configuration.Configuration<K, V> config(DynamicCache options) {
        ResourcePoolsBuilder poolsBuilder = ResourcePoolsBuilder
                // 设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到 offheap 中
                // 基于堆大小的缓存需要打开：--add-opens=java.base/java.util=ALL-UNNAMED
                //.heap(30, MemoryUnit.MB)
                .heap(options.getHeapEntries())
                // 设置堆外内存大小(直接内存) 超出 offheap 的大小会根据淘汰规则被淘汰，数值大小必须小于磁盘大小
                //.offheap(10, MemoryUnit.MB)
                ;
        // 缓存数据K和V的数值类型，在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
        CacheConfigurationBuilder<K, V> builder = CacheConfigurationBuilder.newCacheConfigurationBuilder(options.getKeyType(), options.getValueType(), poolsBuilder)
                // 缓存监听器
                .withService(cacheEventListener());

        if (options.getExpiry() != null) {
            // 数据最大存活时间 TTL
            builder.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(options.getExpiry()));
        }
        return Eh107Configuration.fromEhcacheCacheConfiguration(builder.build());
    }

    private static CacheEventListenerConfiguration<?> cacheEventListener() {
        return CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(
                        new DefaultCacheEventListener(), EventType.EVICTED, EventType.EXPIRED, EventType.REMOVED, EventType.CREATED, EventType.UPDATED)
                .asynchronous()
                .unordered()
                .build();
    }
}
