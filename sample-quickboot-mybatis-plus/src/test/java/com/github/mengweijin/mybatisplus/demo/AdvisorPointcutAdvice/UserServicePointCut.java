package com.github.mengweijin.mybatisplus.demo.AdvisorPointcutAdvice;

import com.github.mengweijin.quickboot.framework.cache.CacheExpire;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UserServicePointCut implements Pointcut, ClassFilter, MethodMatcher {

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return true;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        // 拿到原始方法对象，这个方法上才有注解
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        if (AnnotatedElementUtils.hasAnnotation(specificMethod, CacheExpire.class)) {
            System.err.println("*************************************" + method.getName());
            return true;
        }
        return false;
    }

    /**
     * 这个方法如果返回 true, 会做到动态拦截。在方法调用的时候会再次调用 下面带有参数的 match 方法
     *
     * @return
     */
    @Override
    public boolean isRuntime() {
        return true;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        if (method.getName().equalsIgnoreCase("test")) {
            // 可以再进一步判断方法参数值，这里就不用了
            return true;
        }
        return false;
    }

}
