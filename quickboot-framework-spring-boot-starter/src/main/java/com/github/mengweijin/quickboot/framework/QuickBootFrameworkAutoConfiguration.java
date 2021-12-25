package com.github.mengweijin.quickboot.framework;

import com.github.mengweijin.quickboot.framework.doc.SpringDocAutoConfiguration;
import com.github.mengweijin.quickboot.framework.log.RequestLogAop;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quickboot.framework.web.CorsWebMvcConfigurer;
import com.github.mengweijin.quickboot.framework.web.DefaultExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@EnableConfigurationProperties(AppInfoProperties.class)
public class QuickBootFrameworkAutoConfiguration {

    @Bean
    @Profile({"!prod"})
    @ConditionalOnMissingBean
    public RequestLogAop logAop() {
        return new RequestLogAop();
    }

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
    public CorsWebMvcConfigurer corsWebMvcConfigurer() {
        return new CorsWebMvcConfigurer();
    }
}
