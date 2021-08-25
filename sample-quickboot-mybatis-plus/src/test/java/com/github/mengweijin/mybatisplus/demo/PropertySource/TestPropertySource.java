package com.github.mengweijin.mybatisplus.demo.PropertySource;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class TestPropertySource {

    @Test
    public void test() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.PropertySource";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);
        PropertySourceConfiguration bean = context.getBean(PropertySourceConfiguration.class);
        Assertions.assertEquals("Tom", bean.getName());

        context.close();
    }
}
