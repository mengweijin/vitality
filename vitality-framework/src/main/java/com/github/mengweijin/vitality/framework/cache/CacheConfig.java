package com.github.mengweijin.vitality.framework.cache;

import lombok.AllArgsConstructor;
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
 * Example 1: @Cacheable(value = CacheName.USER, key = CacheConst.KEY_CLASS_METHOD, unless = "#result?.size() == 0")
 * Example 2: @Cacheable(value = CacheName.USER, key = CacheConst.KEY_CLASS + "+#username + 'zhangsan'", unless = "#result == null")
 * <p>
 * 2、使用 {@link CacheFactory}
 *
 * @author mengweijin
 * @since 2022/10/29
 */
@EnableCaching
@Configuration
@AllArgsConstructor
@SuppressWarnings({"rawtypes"})
public class CacheConfig {

}
