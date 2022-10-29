package com.github.mengweijin.quickboot;

import com.github.mengweijin.quickboot.exception.DefaultExceptionHandler;
import com.github.mengweijin.quickboot.log.LogAspect;
import com.github.mengweijin.quickboot.mvc.CorsWebMvcConfigurer;
import com.github.mengweijin.quickboot.response.DefaultResponseBodyAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@EnableAsync
@EnableScheduling
@Configuration
@EnableConfigurationProperties({QuickBootProperties.class})
public class QuickBootAutoConfiguration {

    @Autowired
    private QuickBootProperties quickBootProperties;

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
    @Profile({"!prod"})
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "quickboot.debug", havingValue = "true")
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
