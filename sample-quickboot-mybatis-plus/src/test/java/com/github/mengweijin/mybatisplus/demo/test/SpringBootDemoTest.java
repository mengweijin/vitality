package com.github.mengweijin.mybatisplus.demo.test;

import com.github.mengweijin.mybatisplus.demo.importannotation.Phone1;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone2;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone3;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone4;
import com.github.mengweijin.mybatisplus.demo.scan.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 上下文：AnnotationConfigServletWebServerApplicationContext.java
 * 构造函数中放入：
 * ConfigurationClassPostProcessor.class
 * AutowiredAnnotationBeanPostProcessor.class
 * <p>
 * 添加默认扫描的组件为：
 * Component.class
 * <p>
 * Test ConfigurationClassPostProcessor.java
 * parse @Configuration     - Parse each @Configuration class  源码 331 行
 * 进而在 ConfigurationClassParser.java 这个类按照顺序依次支撑了以下注解：
 * process @Component                               - Recursively process any member (nested) classes first
 * process @PropertySources, PropertySource         - Process any @PropertySource annotations
 * process @ComponentScans, @ComponentScan          - Process any @ComponentScan annotations
 * process @Import                                  - Process any @Import annotations
 * process @ImportResource                          - Process any @ImportResource annotations
 * process @Bean                                    - Process individual @Bean methods
 * processInterfaces(configClass, sourceClass);     - Process default methods on interfaces
 * sourceClass.getMetadata().hasSuperClass()        - Process superclass, if any
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.github.mengweijin.mybatisplus.demo.test")
public class SpringBootDemoTest implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootDemoTest.class, args);

        log.debug(SpringBootDemoTest.applicationContext.getBean(AddressService.class).toString());
        log.debug(SpringBootDemoTest.applicationContext.getBean(Phone1.class).toString());
        log.debug(SpringBootDemoTest.applicationContext.getBean(Phone2.class).toString());
        log.debug(SpringBootDemoTest.applicationContext.getBean(Phone3.class).toString());
        log.debug(SpringBootDemoTest.applicationContext.getBean(Phone4.class).toString());

        context.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
