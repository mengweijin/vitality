package com.github.mengweijin.quickboot.framework.cache;

import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author mengweijin
 */
@Configuration
public class ProxyCachingExpireConfiguration extends AbstractCachingConfiguration {

    /**
     * 重写该方法，把原来的 CacheInterceptor 替换为自己写的 CacheExpireInterceptor。
     *
     * @param cacheOperationSource CacheOperationSource
     * @return CacheExpireInterceptor
     */
    @Bean
    @Primary
    public CacheInterceptor cacheExpireInterceptor(CacheOperationSource cacheOperationSource) {
        CacheInterceptor interceptor = new CacheExpireInterceptor();
        interceptor.configure(this.errorHandler, this.keyGenerator, this.cacheResolver, this.cacheManager);
        interceptor.setCacheOperationSource(cacheOperationSource);
        return interceptor;
    }

}
