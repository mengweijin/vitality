package com.github.mengweijin.generator.config;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.github.mengweijin.generator.DefaultGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;

/**
 * @author Meng Wei Jin
 **/
@EnableCaching
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class GeneratorAutoConfiguration {

    @Bean
    public GeneratorBeanDefinitionRegistryPostProcessor generatorBeanDefinitionRegistryPostProcessor(){
        return new GeneratorBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    public DefaultGenerator defaultGenerator(DataSource dataSource){
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dataSource);
        return new DefaultGenerator(builder.build());
    }
}
