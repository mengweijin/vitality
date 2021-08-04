package com.github.mengweijin.mybatisplus.demo.autowiredmap;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.github.mengweijin.mybatisplus.demo.autowiredmap.component.Apple;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.beans.Introspector;
import java.util.Set;

@Configuration
public class AppleBeanPostProcessor implements BeanFactoryPostProcessor {

    public static String generateBeanName(Class<?> cls) {
        String generatedBeanName = Introspector.decapitalize(cls.getSimpleName());
        return generatedBeanName + "$AppleBean";
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Set<Class<?>> classSet = ClassUtil.scanPackage(Apple.class.getPackage().getName(),
                cls -> !ClassUtil.isAbstract(cls) && Apple.class.isAssignableFrom(cls));
        classSet.forEach(cls -> beanFactory.registerSingleton(generateBeanName(cls), ReflectUtil.newInstance(cls)));
    }
}
