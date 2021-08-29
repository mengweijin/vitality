package com.github.mengweijin.quickboot.framework.cache;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ScheduledFuture;

/**
 * @author mengweijin
 */
@Slf4j
public class CacheExpireInterceptor extends CacheInterceptor {

    /**
     * key: cacheName
     * value: cacheKey
     */
    private final Set<CacheExpireTask> taskCacheSet = new ConcurrentHashSet<>();

    @Autowired
    private CacheManager cacheManager;

    @Qualifier("cachingExpireTaskScheduler")
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public CacheExpireInterceptor() {
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object invoke = super.invoke(invocation);

        submitExpireTask(invocation);

        return invoke;
    }

    public ScheduledFuture<?> submitExpireTask(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        CacheExpire cacheExpire = method.getAnnotation(CacheExpire.class);
        if (cacheExpire == null) {
            return null;
        }

        List<CacheExpireTask> cacheExpireTaskList = this.parseCacheable(invocation);
        if (taskCacheSet.containsAll(cacheExpireTaskList)) {
            // 已经包含了所有的任务，不用重复添加任务了
            return null;
        }

        if (cacheExpire.expire() > 0) {
            this.addCacheTask(cacheExpireTaskList);
            log.debug("Submitted a delay execute expire cache task!");
            return this.submitDelayTask(cacheExpire, cacheExpireTaskList);
        } else if (CronExpression.isValidExpression(cacheExpire.cron())) {
            this.addCacheTask(cacheExpireTaskList);
            log.debug("Submitted a cron execute expire cache task!");
            return this.submitCronTask(cacheExpire, cacheExpireTaskList);
        } else {
            String message = "Invalid @CacheExpire parameters. expire=" + cacheExpire.expire()
                    + ", chronoUnit=" + cacheExpire.chronoUnit()
                    + ", cron=" + cacheExpire.cron();
            log.warn(message);
        }

        return null;
    }

    private ScheduledFuture<?> submitDelayTask(CacheExpire cacheExpire, List<CacheExpireTask> cacheExpireTaskList) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpireTaskList), Instant.now().plus(cacheExpire.expire(), cacheExpire.chronoUnit()));
    }

    /**
     * 1. 判断 cron 是否合法可以使用：CronExpression.isValidExpression(cron)；
     * 2. 也可以直接 CronExpression.parse(cron)；不正确的话会抛异常。
     * 3. CronExpression cronExpression = CronExpression.parse(cron);
     * 4. LocalDateTime nextExecuteTime = cronExpression.next(LocalDateTime.now());
     *
     * @param cacheExpire CacheExpire
     */
    private ScheduledFuture<?> submitCronTask(CacheExpire cacheExpire, List<CacheExpireTask> cacheExpireTaskList) {
        return threadPoolTaskScheduler.schedule(() ->
                this.deleteCache(cacheExpireTaskList), new CronTrigger(cacheExpire.cron()));
    }

    private void deleteCache(List<CacheExpireTask> cacheExpireTaskList) {
        cacheExpireTaskList.forEach(cacheExpireTask -> {
            // 从 cacheManager 中移除
            Cache cache = cacheManager.getCache(cacheExpireTask.getCacheName());
            if (cache != null) {
                cache.evictIfPresent(cacheExpireTask.getCacheKey());
            }
            // 从任务缓存中移除
            taskCacheSet.remove(cacheExpireTask);
        });
    }

    private List<CacheExpireTask> parseCacheable(MethodInvocation invocation) {
        List<CacheExpireTask> list = new ArrayList<>();
        Method method = invocation.getMethod();
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        Object key = getKeyGenerator().generate(invocation.getThis(), method, invocation.getArguments());
        Set<String> cacheNameSet = new TreeSet<>();
        cacheNameSet.addAll(Arrays.asList(cacheable.cacheNames()));
        cacheNameSet.addAll(Arrays.asList(cacheable.value()));
        for (String cacheName : cacheNameSet) {
            list.add(new CacheExpireTask().setCacheName(cacheName).setCacheKey(key));
        }
        return list;
    }

    private void addCacheTask(List<CacheExpireTask> cacheExpireTaskList) {
        if (taskCacheSet.size() + cacheExpireTaskList.size() > Integer.MAX_VALUE - 8) {
            taskCacheSet.clear();
        }
        taskCacheSet.addAll(cacheExpireTaskList);
    }
}
