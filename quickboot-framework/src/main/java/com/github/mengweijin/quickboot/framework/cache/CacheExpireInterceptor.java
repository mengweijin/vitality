package com.github.mengweijin.quickboot.framework.cache;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
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
import java.util.concurrent.ScheduledFuture;

/**
 * Question: is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
 * 如果遇到这个问题，参考以下思路：
 * 实现 ApplicationContextAware 接口和 @Autowired 的区别：
 * 注解的方式的依赖注入发生在当前 bean 实例化完成后，然后根据 @Autowired 实例化依赖的 bean。
 * 假如你自定义了一个后置处理器（实现了 BeanPostProcessor 接口），然而，你又在这个后置处理器中需要依赖一些 bean 来完成功能，
 * 这个时候就体现出来实现 ApplicationContextAware 接口和 @Autowired 的区别。
 * 1. 如果你在后置处理器中使用 @Autowired 注入 bean, 这就导致注入的 bean 会在你这个后置处理器中提前被实例化，而我们知道， AOP 就是通过后置处理器实现的，
 * 一旦注入的 bean 在这里提前实例化，就会导致一些其他的后置处理器无法对注入的 bean 做后置处理了，于是就出现了上面的问题。
 * 2. 如果你在后置处理器中使用实现 ApplicationContextAware 接口的方式来获取 bean，那么就不会导致注入的 bean 会在你这个后置处理器中提前被实例化，
 * 这样就避免了使用 @Autowired 带来的问题。
 * 3. @Bean 和 @Autowired 中的依赖注入同理，所有，如果出现这个问题，在 @Bean 中也不要依赖其他 bean，可以改为实现 Aware 接口的方式。
 * 当然也可以设置 @Configuration(proxyBeanMethods = false) 不让这个配置类中的所有 @Bean 被代理。
 * 总结：
 * 如果普通使用，只做业务处理的 Controller, Service 等，使用 @Autowired 方式很方便。
 * 如果要对 Spring 功能做扩展，优先使用各种 Aware 接口来获取 bean 可以避免一些问题。
 * 如果以上两个不能解决问题，使用 @Configuration(proxyBeanMethods = false) 来阻止配置类中的所有 @Bean 被代理。
 * <p>
 * <p>
 * 如果需要 @CacheExpire 在 @Cacheable 之前执行, @EnableCaching 中指定的 order 需要小于配置中的 order。
 * 如果配置是 @CacheExpire 在 @Cacheable 之后执行，而 @Cacheable 的业务逻辑是如果缓存命中，就不进行火炬传递了，也就是说此时 @CacheExpire 不会执行。
 * 此时需要在 CacheExpireInterceptor 中处理当使用存储到磁盘的缓存管理器时，应用重启后，已经缓存的数据无法使其缓存过期的问题。
 * 此时建议当使用存储到磁盘的缓存管理器时，设置好缓存过期时间，达到双重清理缓存的目的。这样就能同时兼容使用保存在内存或者磁盘的缓存管理器。
 *
 * @author mengweijin
 */
@Slf4j
public class CacheExpireInterceptor implements MethodInterceptor {

    @Getter
    @Setter
    private CacheExpireOperationSource cacheExpireOperationSource;

    @Getter
    @Setter
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        log.debug("Enter CacheExpireInterceptor");
        Method method = invocation.getMethod();
        CacheExpire cacheExpire = method.getAnnotation(CacheExpire.class);
        if (cacheExpire.expire() > 0 || CronExpression.isValidExpression(cacheExpire.cron())) {
            CacheExpireTask cacheExpireTask = null;
            if (method.isAnnotationPresent(Cacheable.class)) {
                cacheExpireTask = cacheExpireOperationSource.parseCacheableAnnotation(invocation);
            } else if (method.isAnnotationPresent(CachePut.class)) {
                cacheExpireTask = cacheExpireOperationSource.parseCachePutAnnotation(invocation);
            } else {
                // do nothing
            }

            // 不为空，并且之前没有添加过这个缓存的 CacheExpire 任务，才进行下面的操作
            if (cacheExpireTask != null) {
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
            log.debug("Submitted a delay execute expire cache task!");
            this.submitDelayTask(cacheExpire, cacheExpireTask);
        } else if (CronExpression.isValidExpression(cacheExpire.cron())) {
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
    }

}
