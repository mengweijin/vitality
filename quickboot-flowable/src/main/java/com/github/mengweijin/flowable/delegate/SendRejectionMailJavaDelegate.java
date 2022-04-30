package com.github.mengweijin.flowable.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author mengweijin
 * @date 2022/4/10
 */
public class SendRejectionMailJavaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println(execution.getVariable("employee") + ": 已发送拒绝休假的邮件通知！");
    }
}
