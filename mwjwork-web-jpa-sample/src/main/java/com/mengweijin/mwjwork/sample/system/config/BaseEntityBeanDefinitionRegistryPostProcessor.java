package com.mengweijin.mwjwork.sample.system.config;

import cn.hutool.core.util.ClassUtil;
import com.mengweijin.mwjwork.framework.jpa.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author mengweijin
 */
@Component
@Slf4j
public class BaseEntityBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 将所有BaseEntity的子类注册到spring中
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.mengweijin", BaseEntity.class);
        classes.forEach(cls -> {
            if (!cls.isInterface() && !ClassUtil.isAbstract(cls)) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(cls);
                registry.registerBeanDefinition(BeanDefinitionReaderUtils.generateBeanName(beanDefinition, registry), beanDefinition);
            }
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
