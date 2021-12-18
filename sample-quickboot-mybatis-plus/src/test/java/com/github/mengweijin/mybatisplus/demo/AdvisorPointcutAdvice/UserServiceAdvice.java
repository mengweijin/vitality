package com.github.mengweijin.mybatisplus.demo.AdvisorPointcutAdvice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.err.println("----------------------------------------" + "Interceptor");
        return invocation.proceed();
    }
}
