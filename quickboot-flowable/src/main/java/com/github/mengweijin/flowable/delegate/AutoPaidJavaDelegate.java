package com.github.mengweijin.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author mengweijin
 * @date 2022/4/10
 */
public class AutoPaidJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        //System.out.println(execution.getVariable("employee") + " 的请假申请已获批准！");
    }
}
