package com.github.mengweijin.vitality.framework;

import com.github.mengweijin.vitality.framework.constant.Const;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

/**
 * @author mengweijin
 * @since 2022/7/27
 */
public class VitalityMapperBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitalityMapperBeanDefinitionRegistryPostProcessor.class);
        String parentPkg = StrUtil.subBefore(pkg, Const.DOT, true);
        ClassPathMapperScanner mapperScanner = new ClassPathMapperScanner(beanDefinitionRegistry, applicationContext.getEnvironment());
        mapperScanner.registerFilters();
        mapperScanner.scan(parentPkg);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
