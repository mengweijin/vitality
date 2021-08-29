package com.github.mengweijin.quickboot.framework.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

/**
 * @author Meng Wei Jin
 * @description EnableCaching 开启缓存
 **/
@Configuration
@EnableCaching
@Import({
        ProxyCachingExpireConfiguration.class
})
public class QuickBootCacheAutoConfiguration {

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
    public ThreadPoolTaskScheduler cachingExpireTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("task-scheduler-caching-expire-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setWaitForTasksToCompleteOnShutdown(false);
        scheduler.setAwaitTerminationSeconds(0);
        // ThreadPoolTaskScheduler 在处理任务时，如果抛出异常，就可以通过这个类得到异常信息。
        scheduler.setErrorHandler(new SchedulerErrorHandler());

        return scheduler;
    }

    @Slf4j
    private static class SchedulerErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            log.error(t.getMessage(), t);
        }
    }

}
