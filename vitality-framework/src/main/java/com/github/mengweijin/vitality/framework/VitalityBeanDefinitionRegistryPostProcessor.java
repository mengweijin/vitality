package com.github.mengweijin.vitality.framework;

import com.github.mengweijin.vitality.framework.constant.Const;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;

/**
 * @author mengweijin
 * @since 2022/7/27
 */
public class VitalityBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitalityBeanDefinitionRegistryPostProcessor.class);
        String parentPkg = StrUtil.subBefore(pkg, Const.DOT, true);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.scan(parentPkg);
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
