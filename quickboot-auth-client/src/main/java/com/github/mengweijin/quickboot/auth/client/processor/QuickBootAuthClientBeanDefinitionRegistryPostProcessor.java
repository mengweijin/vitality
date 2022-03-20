package com.github.mengweijin.quickboot.auth.client.processor;

import com.github.mengweijin.quickboot.auth.client.QuickBootAuthClientAutoConfiguration;
import com.github.mengweijin.quickboot.auth.client.utils.Tools;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author mengweijin
 * @date 2022/1/9
 */
public class QuickBootAuthClientBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        //根据BeanDefinitionRegistry对象获创建注解扫描器(Spring源码实现也是通过new 的方式)
        //ClassPathBeanDefinitionScanner类就是自定义注解的支撑
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        // 添加自定义注解到注册器
        // 中（可以添加多个实现了 TypeFilter 的过滤器）
        //scanner.addIncludeFilter(new AnnotationTypeFilter(MyComponent.class));
        // 指定注解扫描器的包路径(字符串)

        String basePackage = Tools.getPackage(QuickBootAuthClientAutoConfiguration.class);
        scanner.scan(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // do nothing
    }

}
