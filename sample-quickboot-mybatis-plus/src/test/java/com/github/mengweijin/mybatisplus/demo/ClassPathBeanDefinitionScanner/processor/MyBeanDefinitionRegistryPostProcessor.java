package com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner.processor;

import com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner.MyComponent.MyComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/**
 * 扫描指定目录下所有添加了自定义的 @MyComponent 注解的类，并为其创建 BeanDefinition 对象，最终初始化到 Spring 容器中。
 * ClassPathBeanDefinitionScanner 继承了 ClassPathScanningCandidateComponentProvider 类。
 * ClassPathBeanDefinitionScanner 应用还有 MyBatis 中的 ClassPathMapperScanner 继承了 ClassPathBeanDefinitionScanner。
 * ClassPathScanningCandidateComponentProvider 的应用还有 @ServletComponentScan 注解，扫描类中包含 @WebServlet, @WebFilter, @WebListener 注解。
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //根据BeanDefinitionRegistry对象获创建注解扫描器(Spring源码实现也是通过new 的方式)
        //ClassPathBeanDefinitionScanner类就是自定义注解的支撑
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        // 添加自定义注解到注册器中
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));
        // 指定注解扫描器的包路径(字符串)
        scanner.scan("com.github.mengweijin.mybatisplus.demo.ClassPathBeanDefinitionScanner.MyComponent");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
