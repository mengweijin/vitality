package com.github.mengweijin.quickboot.framework.orika;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.Set;

/**
 * 在这里如果注册OrikaFieldMapping到实现类，扫描包路径时：
 * 1. 如果写死，如：com.mengweijin, 那么子工程在依赖的时候，包名就必须以com.mengweijin开头，这样不够灵活；
 * 2. 如果写为ClassUtil.scanPackageBySuper(null, OrikaFieldMapping.class); 即扫描类加载器加载的所有的类，这样应用程序启动时耗时太长；
 *
 * 因此目前推荐的做法是：在OrikaFieldMapping的实现类上加@Component注解来注册到spring容器中
 * @author mengweijin
 */
@Deprecated
//@Component
@Slf4j
public class OrikaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 将所有OrikaMapperFactoryConfigurer的子类注册到spring中
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // packageName=null: 扫描类加载器加载的所有的类
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(null, OrikaFieldMapping.class);
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
