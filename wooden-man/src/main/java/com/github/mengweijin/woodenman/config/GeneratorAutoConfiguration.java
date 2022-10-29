package com.github.mengweijin.woodenman.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Meng Wei Jin
 **/
@EnableCaching
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class GeneratorAutoConfiguration {

}
