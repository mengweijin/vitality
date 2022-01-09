package com.github.mengweijin.quickboot.auth.security.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 登录成功监听。
 * 虽然这里可以监听到认证成功的事件，但是拿不到 HttpServletRequest 对象中的参数。
 * 因此，如果你的业务逻辑需要从 HttpServletRequest 中获取一些参数，就不能用这个了，可以实现 SavedRequestAwareAuthenticationSuccessHandler 类来做。
 * 参考自定义实现类：QuickBootAuthenticationSuccessHandler
 *
 * @author Meng Wei Jin
 */
//@Component
@Deprecated
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails)event.getAuthentication().getDetails();
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        String ip = auth.getRemoteAddress();
    }
}