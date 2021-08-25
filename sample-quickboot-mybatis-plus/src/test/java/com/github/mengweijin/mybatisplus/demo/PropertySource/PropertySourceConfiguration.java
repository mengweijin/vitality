package com.github.mengweijin.mybatisplus.demo.PropertySource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:test.properties")
public class PropertySourceConfiguration {

    @Value("${test.name}")
    private String name;
}
