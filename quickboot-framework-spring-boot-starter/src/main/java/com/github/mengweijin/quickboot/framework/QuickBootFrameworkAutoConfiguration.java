package com.github.mengweijin.quickboot.framework;

import com.github.mengweijin.quickboot.framework.aspectj.LogAspect;
import com.github.mengweijin.quickboot.framework.filter.repeatable.RepeatableFilter;
import com.github.mengweijin.quickboot.framework.filter.xss.XssFilter;
import com.github.mengweijin.quickboot.framework.filter.xss.XssProperties;
import com.github.mengweijin.quickboot.framework.mvc.CorsWebMvcConfigurer;
import com.github.mengweijin.quickboot.framework.exception.DefaultExceptionHandler;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.jsoup.Jsoup;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

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
@EnableConfigurationProperties({AppInfoProperties.class, QuickBootProperties.class})
public class QuickBootFrameworkAutoConfiguration {

    @Autowired
    private AppInfoProperties appInfoProperties;

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
    public CorsWebMvcConfigurer corsWebMvcConfigurer() {
        return new CorsWebMvcConfigurer();
    }

    @Bean
    public FilterRegistrationBean<RepeatableFilter> repeatableFilter() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jsoup.class)
    public FilterRegistrationBean<XssFilter> xssFilter() {
        XssProperties xssProperties = quickBootProperties.getXss();
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put(XssFilter.EXCLUDES, String.join(Const.COMMA, xssProperties.getExcludes()));
        initParameters.put(XssFilter.ENABLED, String.valueOf(xssProperties.getEnabled()));
        registration.setInitParameters(initParameters);
        return registration;
    }

    @Bean
    @Profile({"!prod"})
    @ConditionalOnMissingBean
    public LogAspect logAspect() {
        return new LogAspect(appLog -> {});
    }

    /**
     * 可以多添加几个这样的 bean,
     * 按照 .pathsToMatch(Constants.ALL_PATTERN)
     * 或者 .packagesToScan("com.github.mengweijin")
     * 来分组展示。
     */
    @Bean
    @Profile({"!prod"})
    @ConditionalOnClass({GroupedOpenApi.class})
    public GroupedOpenApi applicationAllApi() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch(Constants.ALL_PATTERN)
                .addOpenApiCustomiser(openApi ->
                        openApi.info(new Info().title("All APIs")
                                .version(appInfoProperties.getVersion()))
                )
                .build();
    }
}
