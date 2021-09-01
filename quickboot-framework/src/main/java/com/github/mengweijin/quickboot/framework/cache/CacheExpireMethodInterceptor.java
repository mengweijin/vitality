package com.github.mengweijin.quickboot.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

/**
 * @author mengweijin
 */
@Slf4j
public class CacheExpireMethodInterceptor implements MethodInterceptor {

    private final ConcurrentLinkedQueue<CacheExpireTask> taskCache = new ConcurrentLinkedQueue<>();

    @Autowired
    private AnnotationArgsParser annotationArgsParser;

    @Qualifier("cachingExpireTaskScheduler")
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        CacheExpire cacheExpire = method.getAnnotation(CacheExpire.class);
        if (cacheExpire.expire() > 0 || CronExpression.isValidExpression(cacheExpire.cron())) {
            CacheExpireTask cacheExpireTask = null;
            if (method.isAnnotationPresent(Cacheable.class)) {
                cacheExpireTask = annotationArgsParser.parseCacheableAnnotation(invocation);
            } else if (method.isAnnotationPresent(CachePut.class)) {
                cacheExpireTask = annotationArgsParser.parseCachePutAnnotation(invocation);
            } else {
                // do nothing
            }

            // 不为空，并且之前没有添加过这个缓存的 CacheExpire 任务，才进行下面的操作
            if (cacheExpireTask != null && !taskCache.contains(cacheExpireTask)) {
                this.submitExpireTask(cacheExpire, cacheExpireTask);
            }
        } else {
            String message = "Invalid @CacheExpire parameters. expire=" + cacheExpire.expire()
                    + ", chronoUnit=" + cacheExpire.chronoUnit()
                    + ", cron=" + cacheExpire.cron();
            log.warn(message);
        }

        return invocation.proceed();
    }

    public void submitExpireTask(CacheExpire cacheExpire, CacheExpireTask cacheExpireTask) {
        if (cacheExpire.expire() > 0) {
            taskCache.offer(cacheExpireTask);
            log.debug("Submitted a delay execute expire cache task!");
            this.submitDelayTask(cacheExpire, cacheExpireTask);
        } else if (CronExpression.isValidExpression(cacheExpire.cron())) {
            taskCache.offer(cacheExpireTask);
            log.debug("Submitted a cron execute expire cache task!");
            this.submitCronTask(cacheExpire, cacheExpireTask);
        }
    }

    private ScheduledFuture<?> submitDelayTask(CacheExpire cacheExpire, CacheExpireTask cacheExpireTask) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpireTask), Instant.now().plus(cacheExpire.expire(), cacheExpire.chronoUnit()));
    }

    /**
     * 1. 判断 cron 是否合法可以使用：CronExpression.isValidExpression(cron)；
     * 2. 也可以直接 CronExpression.parse(cron)；不正确的话会抛异常。
     * 3. CronExpression cronExpression = CronExpression.parse(cron);
     * 4. LocalDateTime nextExecuteTime = cronExpression.next(LocalDateTime.now());
     *
     * @param cacheExpire CacheExpire
     */
    private ScheduledFuture<?> submitCronTask(CacheExpire cacheExpire, CacheExpireTask cacheExpireTask) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpireTask), new CronTrigger(cacheExpire.cron()));
    }

    private void deleteCache(CacheExpireTask cacheExpireTask) {
        CacheManager cacheManager = cacheExpireTask.getCacheManager();
        cacheExpireTask.getCacheNames().forEach(cacheName -> {
            // 从 cacheManager 中移除
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.evictIfPresent(cacheExpireTask.getCacheKey());
            }
        });

        // 从任务缓存中移除
        taskCache.remove(cacheExpireTask);
    }
}
