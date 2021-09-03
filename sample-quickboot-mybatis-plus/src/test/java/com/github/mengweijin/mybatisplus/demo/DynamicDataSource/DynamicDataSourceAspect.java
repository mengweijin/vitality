package com.github.mengweijin.mybatisplus.demo.DynamicDataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {
    @Pointcut("@annotation(com.github.mengweijin.mybatisplus.demo.DynamicDataSource.DS)")
    public void dsPointCut() {
    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        DS ds = method.getAnnotation(DS.class);

        if (ds != null) {
            DynamicDataSourceHolder.getDataSourceThreadLocal().set(ds.value());
        }

        try {
            return joinPoint.proceed();
        } finally {
            // 在执行方法之后，从 threadLocal 中移除
            DynamicDataSourceHolder.getDataSourceThreadLocal().remove();
        }
    }
}
