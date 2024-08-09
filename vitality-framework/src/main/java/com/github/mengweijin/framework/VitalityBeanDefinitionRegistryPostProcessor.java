package com.github.mengweijin.framework;

import com.github.mengweijin.framework.constant.Const;
import jakarta.validation.constraints.NotNull;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author mengweijin
 * @since 2022/7/27
 */
public class VitalityBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NotNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitalityBeanDefinitionRegistryPostProcessor.class);
        String parentPkg = StrUtil.subBefore(pkg, Const.DOT, true);

        ClassPathMapperScanner mapperScanner = new ClassPathMapperScanner(beanDefinitionRegistry);
        mapperScanner.registerFilters();
        mapperScanner.scan(parentPkg);

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.scan(parentPkg);
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
