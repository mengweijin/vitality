package com.github.mengweijin.quickboot.framework;

import com.github.mengweijin.quickboot.framework.aspectj.LogAspect;
import com.github.mengweijin.quickboot.framework.exception.DefaultExceptionHandler;
import com.github.mengweijin.quickboot.framework.filter.repeatable.RepeatableFilter;
import com.github.mengweijin.quickboot.framework.mvc.CorsWebMvcConfigurer;
import com.github.mengweijin.quickboot.framework.response.DefaultResponseBodyAdvice;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * 当任务新增进来时：
 * 1. 当前运行的线程 < corePoolSize 时，新起一个线程执行新增进来的任务；
 * 2. 当前运行的线程 >= corePoolSize 时，新增进来的任务添加到阻塞队列；
 * 3. 阻塞队列已经满了时，但当前运行线程数 < maxPoolSize, 新起一个线程执行新增进来的任务；
 * 4. 阻塞队列已经满了时，并且当前运行线程数 = maxPoolSize，执行任务丢弃策略。
 *
 * @author mengweijin
 */
@EnableCaching
@EnableAsync
@EnableScheduling
@Configuration
@EnableConfigurationProperties({AppInfoProperties.class, QuickBootProperties.class})
public class QuickBootFrameworkAutoConfiguration {

    @Autowired
    private QuickBootProperties quickBootProperties;

    /**
     * 为什么这里要加 static? 必须要标记为 static 方法，以示优先加载。否则会给出警告。
     * ConfigurationClassEnhancer
     * : @Bean method QuickBootFrameworkAutoConfiguration.springUtils is non-static and returns an object assignable to
     * Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations
     * such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class.
     * Add the 'static' modifier to this method to avoid these container lifecycle issues;
     * see @Bean javadoc for complete details.
     */
    @Bean
    @ConditionalOnMissingBean
    public static SpringUtils springUtils() {
        return new SpringUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultExceptionHandler defaultExceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultResponseBodyAdvice defaultResponseBodyAdvice() {
        return new DefaultResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsWebMvcConfigurer corsWebMvcConfigurer() {
        return new CorsWebMvcConfigurer();
    }

    @Bean
    public FilterRegistrationBean<RepeatableFilter> repeatableFilter() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @Profile({"!prod"})
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect(appLog -> {});
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return (factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
            factory.addErrorPages(errorPage404);
        });
    }
}
