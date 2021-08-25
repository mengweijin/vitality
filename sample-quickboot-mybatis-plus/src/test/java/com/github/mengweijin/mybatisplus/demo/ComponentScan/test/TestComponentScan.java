package com.github.mengweijin.mybatisplus.demo.ComponentScan.test;

import com.github.mengweijin.mybatisplus.demo.ComponentScan.entity.ComponentScanEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class TestComponentScan {

    @Test
    public void componentScan() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.ComponentScan.test";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);

        Assertions.assertNotNull(context.getBean(ComponentScanEntity.class));
        context.close();
    }
}
