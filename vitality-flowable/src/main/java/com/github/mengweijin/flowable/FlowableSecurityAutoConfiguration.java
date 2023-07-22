package com.github.mengweijin.flowable;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.ui.common.security.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 去掉登录验证后，直接访问：http://localhost:8081/modeler/#/processes
 *
 * Order配置说明
 * 这个地方相同会报错
 * 这个地方如果大于则该配置在FlowableUiSecurityAutoConfiguratio中对应项后加载，不能起到绕过授权作用
 * 所以这个地方-1让该配置项在FlowableUiSecurityAutoConfiguratio中对应配置项前加载，以跳过授权
 *
 * @author Meng Wei Jin
 **/
@Order(SecurityConstants.FORM_LOGIN_SECURITY_ORDER - 1)
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class FlowableSecurityAutoConfiguration implements WebMvcConfigurer, EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/**"));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        // 放行资源目录
                        authorize.requestMatchers(new AntPathRequestMatcher("/modeler/**")).permitAll()
                        // 其余的全部放行
                        .anyRequest().permitAll()
                );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * 解决中文乱码
     */
    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        //engineConfiguration.setActivityFontName("宋体");
        //engineConfiguration.setLabelFontName("宋体");
        //engineConfiguration.setAnnotationFontName("宋体");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    //@Bean
    public FlowableBeanDefinitionRegistryPostProcessor flowableBeanDefinitionRegistryPostProcessor() {
        return new FlowableBeanDefinitionRegistryPostProcessor();
    }
}
