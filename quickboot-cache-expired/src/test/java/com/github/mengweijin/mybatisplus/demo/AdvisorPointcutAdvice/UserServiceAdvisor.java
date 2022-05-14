package com.github.mengweijin.mybatisplus.demo.AdvisorPointcutAdvice;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAdvisor implements PointcutAdvisor {

    @Autowired
    private UserServicePointCut userServicePointCut;

    @Autowired
    private UserServiceAdvice userServiceAdvice;


    @Override
    public Pointcut getPointcut() {
        return this.userServicePointCut;
    }

    @Override
    public Advice getAdvice() {
        return this.userServiceAdvice;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
