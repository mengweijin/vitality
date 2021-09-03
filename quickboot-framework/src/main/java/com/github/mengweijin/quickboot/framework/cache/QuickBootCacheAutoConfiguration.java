package com.github.mengweijin.quickboot.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.annotation.ProxyCachingConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Meng Wei Jin
 * @description EnableCaching 开启缓存
 **/
@Slf4j
@Configuration
@EnableCaching
@AutoConfigureAfter({ProxyCachingConfiguration.class})
public class QuickBootCacheAutoConfiguration {

    /**
     * AnnotationMatchingPointcut.java 拦截注解的 Pointcut, 构造函数中参数如下：
     * classAnnotationType: 拦截类上面指定的注解，如果只拦截方法上的注解，需要设置为 null
     * methodAnnotationType: 拦截方法上面指定的注解。
     * checkInherited: 是否检查父类
     *
     * @return
     */
    @Bean
    public Advisor cacheExpireAdvisor() {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheExpire.class, true);
        // 配置增强类 advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(cacheExpireMethodInterceptor());
        // 如果需要在 @Cacheable 等注解的 AOP 后面执行 @CacheExpire 的 AOP, 可以在 @EnableCaching 中指定 order 小于这个 order.
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);

        return advisor;
    }

    @Bean
    public MethodInterceptor cacheExpireMethodInterceptor() {
        return new CacheExpireMethodInterceptor();
    }

    @Bean
    public AnnotationArgsParser annotationArgsParser() {
        return new AnnotationArgsParser();
    }

    /**
     * Caching Expire TaskScheduler
     * <p>
     * 提示：在 Spring 中我们通过 @Scheduled 注解来实现的定时任务，底层也是通过 ThreadPoolTaskScheduler 来实现的。
     * 可以通过 ScheduledAnnotationBeanPostProcessor 类来查看。
     * ThreadPoolTaskScheduler 的默认线程数是 1，这个需要根据实际的情况进行修改。
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolTaskScheduler cachingExpireTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("task-scheduler-caching-expire-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        scheduler.setAwaitTerminationSeconds(0);
        // ThreadPoolTaskScheduler 在处理任务时，如果抛出异常，就可以通过这个类得到异常信息。
        scheduler.setErrorHandler(throwable -> log.error(throwable.getMessage(), throwable));

        return scheduler;
    }
}
