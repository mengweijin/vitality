package com.mwj.mwjwork.framework.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Meng Wei Jin
 * @description
 * @EnableJpaRepositories 一定要配置basePackages，或者在启动类上配置
 * @date Create in 2019-08-12 19:58
 **/
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(repositoryBaseClass = SimpleBaseRepository.class, basePackages = {"com.mwj"})
public class JpaConfig {
}
