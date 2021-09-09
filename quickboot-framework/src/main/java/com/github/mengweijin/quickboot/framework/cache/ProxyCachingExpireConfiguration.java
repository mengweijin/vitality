package com.github.mengweijin.quickboot.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * {@code @Configuration} class that registers the Spring infrastructure beans necessary
 * to enable proxy-based annotation-driven cache expire management.
 * <p>
 * 参考类 ProxyCachingConfiguration 写的。
 * 注意：@Configuration(proxyBeanMethods = false) 和 @Role(BeanDefinition.ROLE_INFRASTRUCTURE) 很重要。参考 CacheExpireInterceptor 类注释中的问题。
 *
 * @author mengweijin
 * @see CacheExpireOperationSource
 * @see CacheExpireInterceptor
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@AutoConfigureAfter({QuickBootCacheAutoConfiguration.class})
public class ProxyCachingExpireConfiguration {

    public static final String CACHING_THREAD_POOL_NAME = "cachingExpireTaskScheduler";

    /**
     * AnnotationMatchingPointcut.java 拦截注解的 Pointcut, 构造函数中参数如下：
     * classAnnotationType: 拦截类上面指定的注解，如果只拦截方法上的注解，需要设置为 null
     * methodAnnotationType: 拦截方法上面指定的注解。
     * checkInherited: 是否检查父类
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor cacheExpireAdvisor(CacheExpireInterceptor cacheExpireInterceptor) {
        log.info("Create bean cacheExpireAdvisor");
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheExpire.class, true);
        // 配置增强类 advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(cacheExpireInterceptor);
        // 如果需要 @CacheExpire 在 @Cacheable 之前执行, @EnableCaching 中指定的 order 需要小于这个 order。
        // 下面这个配置是 @CacheExpire 在 @Cacheable 之后执行，而 @Cacheable 的业务逻辑是如果缓存命中，就不进行火炬传递了，也就是说此时 @CacheExpire 不会执行。
        // 需要在 CacheExpireInterceptor 中处理当使用存储到磁盘的缓存管理器时，应用重启后，已经缓存的数据无法使其缓存过期的问题。
        // 此时建议当使用存储到磁盘的缓存管理器时，设置好缓存过期时间，达到双重清理缓存的目的。这样就能同时兼容使用保存在内存或者磁盘的缓存管理器。
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);

        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheExpireOperationSource cacheExpireOperationSource() {
        return new CacheExpireOperationSource();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheExpireInterceptor cacheExpireMethodInterceptor(
            CacheExpireOperationSource cacheExpireOperationSource,
            @Qualifier(ProxyCachingExpireConfiguration.CACHING_THREAD_POOL_NAME) ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        CacheExpireInterceptor interceptor = new CacheExpireInterceptor();
        interceptor.setCacheExpireOperationSource(cacheExpireOperationSource);
        interceptor.setThreadPoolTaskScheduler(threadPoolTaskScheduler);

        return interceptor;
    }

    /**
     * Caching Expire TaskScheduler
     * <p>
     * 提示：在 Spring 中我们通过 @Scheduled 注解来实现的定时任务，底层也是通过 ThreadPoolTaskScheduler 来实现的。
     * 可以通过 ScheduledAnnotationBeanPostProcessor 类来查看。
     * ThreadPoolTaskScheduler 的默认线程数是 1，这个需要根据实际的情况进行修改。
     * </p>
     *
     * @return ThreadPoolTaskScheduler
     */
    @Bean(ProxyCachingExpireConfiguration.CACHING_THREAD_POOL_NAME)
    @ConditionalOnMissingBean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ThreadPoolTaskScheduler cachingExpireTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("task-scheduler-caching-expire-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        scheduler.setAwaitTerminationSeconds(0);
        // ThreadPoolTaskScheduler 在处理任务时，如果抛出异常，就可以通过这个类得到异常信息。
        scheduler.setErrorHandler(throwable -> log.error(throwable.getMessage(), throwable));

        return scheduler;
    }

}
