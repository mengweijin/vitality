package com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner;

import com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner.MyComponent.UserComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyComponentTest {

    @Test
    public void test() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner.processor";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);

        UserComponent bean = context.getBean(UserComponent.class);
        Assertions.assertNotNull(bean);

        context.close();
    }
}
