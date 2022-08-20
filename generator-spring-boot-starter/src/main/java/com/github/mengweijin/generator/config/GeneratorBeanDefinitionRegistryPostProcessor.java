package com.github.mengweijin.generator.config;

import cn.hutool.core.util.ClassUtil;
import com.github.mengweijin.generator.DefaultGenerator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author mengweijin
 * @date 2022/7/27
 */
public class GeneratorBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        String quickBootPackage = ClassUtil.getPackage(DefaultGenerator.class);
        scanner.scan(quickBootPackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

}
