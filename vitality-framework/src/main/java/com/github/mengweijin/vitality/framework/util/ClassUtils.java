package com.github.mengweijin.vitality.framework.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
public class ClassUtils {

    private ClassUtils() {}

    public static Class<?> getMainClass() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            StackTraceElement[] stackTraceArray = e.getStackTrace();
            Optional<StackTraceElement> first = Arrays.stream(stackTraceArray).filter(element -> "main".equals(element.getMethodName())).findFirst();
            StackTraceElement mainElement = first.get();
            // com.github.mengweijin.generator.core.DefaultGenerator
            String className = mainElement.getClassName();
            return ClassUtil.loadClass(className, false);
        }
    }

    public static String getMainClassPackage() {
        return ClassUtil.getPackage(getMainClass());
    }

    public static Class<?> getSpringBootApplicationClass() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
        return annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass();
    }

    public static String getSpringBootApplicationClassPackage() {
        return ClassUtil.getPackage(getSpringBootApplicationClass());
    }

}
