package com.github.mengweijin.mybatisplus.demo.DynamicDataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfiguration {

    @Bean("masterDataSource")
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return new HikariDataSource();
    }

    @Bean("slaveDataSource")
    @ConfigurationProperties("spring.datasource.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public DynamicDataSource dataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DynamicDataSourceHolder.MASTER, masterDataSource());
        targetDataSources.put(DynamicDataSourceHolder.SLAVE, slaveDataSource());

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setDefaultTargetDataSource(masterDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        return dataSource;
    }
}
