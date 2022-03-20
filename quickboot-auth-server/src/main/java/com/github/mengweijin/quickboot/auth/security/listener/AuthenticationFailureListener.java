package com.github.mengweijin.quickboot.auth.security.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 登录失败监听。
 * 和 AuthenticationSuccessEventListener 类的情况类时，为何废弃，请参考 AuthenticationSuccessEventListener 类上面的注释。
 * @deprecated deprecated
 * @author Meng Wei Jin
 **/
@Slf4j
//@Component
@Deprecated
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        String username = String.valueOf(event.getAuthentication().getPrincipal());
        String ip = auth.getRemoteAddress();
        AuthenticationException exception = event.getException();

    }
}
