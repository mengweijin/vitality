package com.github.mengweijin.framework.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author mengweijin
 * @since 2022/11/19
 */
public class JoinPointUtils {

    /**
     * 获取方法上指定的注解
     *
     * @param joinPoint       JoinPoint
     * @param annotationClass annotationClass
     * @param <T>             T extends Annotation
     * @return T extends Annotation
     */
    public static <T extends Annotation> T getMethodAnnotation(JoinPoint joinPoint, Class<T> annotationClass) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method == null ? null : method.getAnnotation(annotationClass);
    }
}
