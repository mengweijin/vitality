package com.github.mengweijin.cache.expired;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author mengweijin
 */
public class CacheExpiredOperationSource implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public CacheExpiredTask parseCacheableAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        Cacheable cacheable = method.getAnnotation(Cacheable.class);

        KeyGenerator keyGenerator;
        if (StringUtils.hasText(cacheable.key())) {
            keyGenerator = applicationContext.getBean(cacheable.keyGenerator(), KeyGenerator.class);
        } else {
            keyGenerator = applicationContext.getBean(CacheInterceptor.class).getKeyGenerator();
        }

        CacheManager cacheManager;
        if (StringUtils.hasText(cacheable.cacheManager())) {
            cacheManager = applicationContext.getBean(cacheable.cacheManager(), CacheManager.class);
        } else {
            cacheManager = applicationContext.getBean(CacheManager.class);
        }

        Object key = keyGenerator.generate(invocation.getThis(), method, invocation.getArguments());

        Set<String> cacheNameSet = new TreeSet<>();
        cacheNameSet.addAll(Arrays.asList(cacheable.cacheNames()));
        cacheNameSet.addAll(Arrays.asList(cacheable.value()));

        return new CacheExpiredTask().setCacheKey(key).setCacheNames(cacheNameSet).setCacheManager(cacheManager);
    }

    public CacheExpiredTask parseCachePutAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        CachePut cachePut = method.getAnnotation(CachePut.class);

        KeyGenerator keyGenerator;
        if (StringUtils.hasText(cachePut.key())) {
            keyGenerator = applicationContext.getBean(cachePut.keyGenerator(), KeyGenerator.class);
        } else {
            keyGenerator = applicationContext.getBean(CacheInterceptor.class).getKeyGenerator();
        }

        CacheManager cacheManager;
        if (StringUtils.hasText(cachePut.cacheManager())) {
            cacheManager = applicationContext.getBean(cachePut.cacheManager(), CacheManager.class);
        } else {
            cacheManager = applicationContext.getBean(CacheManager.class);
        }

        Object key = keyGenerator.generate(invocation.getThis(), method, invocation.getArguments());

        Set<String> cacheNameSet = new TreeSet<>();
        cacheNameSet.addAll(Arrays.asList(cachePut.cacheNames()));
        cacheNameSet.addAll(Arrays.asList(cachePut.value()));

        return new CacheExpiredTask().setCacheKey(key).setCacheNames(cacheNameSet).setCacheManager(cacheManager);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
