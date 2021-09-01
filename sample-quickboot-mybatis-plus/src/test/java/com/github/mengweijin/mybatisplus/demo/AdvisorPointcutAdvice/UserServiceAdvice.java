package com.github.mengweijin.mybatisplus.demo.AdvisorPointcutAdvice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Component
public class UserServiceAdvice implements MethodInterceptor {
    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        System.err.println("----------------------------------------" + "Interceptor");
        return invocation.proceed();
    }
}
