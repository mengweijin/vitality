package com.mengweijin.mwjwork.framework.cache;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Meng Wei Jin
 * @description EnableCaching 开启缓存
 **/
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * If the CacheManager is auto-configured by Spring Boot,
     * you can further tune its configuration before it is fully initialized by exposing a bean that implements the CacheManagerCustomizer interface.
     *
     * @return CacheManagerCustomizer
     */
    @Bean
    public CacheManagerCustomizer<JCacheCacheManager> cacheManagerCustomizer() {
        return cacheManager -> cacheManager.setAllowNullValues(true);
    }

}
