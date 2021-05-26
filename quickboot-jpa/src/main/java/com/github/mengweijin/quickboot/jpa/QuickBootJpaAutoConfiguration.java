package com.github.mengweijin.quickboot.jpa;

import com.github.mengweijin.quickboot.jpa.page.JpaPageArgumentResolver;
import com.github.mengweijin.quickboot.jpa.page.PageResponseBodyAdvice;
import com.github.mengweijin.quickboot.jpa.repository.SimpleBaseJpaRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Meng Wei Jin
 * @description
 * @EnableJpaRepositories 一定要配置basePackages，或者在启动类上配置这个注解，否则启动报错
 * @date Create in 2019-08-12 19:58
 **/
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = SimpleBaseJpaRepository.class, basePackages = {"com"})
public class QuickBootJpaAutoConfiguration {

    /**
     * 自动填充 @CreatedBy， @LastModifiedBy
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AuditorAware getAuditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public JpaPageArgumentResolver pagerArgumentResolver(){
        return new JpaPageArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public PageResponseBodyAdvice pageResponseBodyAdvice(){
        return new PageResponseBodyAdvice();
    }
}
