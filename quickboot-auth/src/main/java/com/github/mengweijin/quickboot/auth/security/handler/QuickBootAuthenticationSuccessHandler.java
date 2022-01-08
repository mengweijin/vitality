package com.github.mengweijin.quickboot.auth.security.handler;

import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在 spring security 主流程 {@link AbstractAuthenticationProcessingFilter } 中的 doFilter 会处理认证成功，认证失败的逻辑。
 * 在这个类中，我们可以发现成功和失败的默认处理 Handler 为：
 * 	private {@link AuthenticationSuccessHandler} successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
 * 	private {@link AuthenticationFailureHandler} failureHandler = new SimpleUrlAuthenticationFailureHandler();
 *
 * 	而在 {@link WebSecurityConfigurerAdapter } 类中，是可以自己指定的，如下：
 * 	http.formLogin().successHandler(successHandler).failureHandler(failureHandler).permitAll();
 *
 * 	那么我们要扩展的话，就可以自己实现一个类，继承上面的类，再加入自己的业务逻辑。
 *
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class QuickBootAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 先实现自己的业务逻辑
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        // 异步记录登录成功的日志
        loginLogTask.addSuccessLoginLog(request, username);

        // 再调用父类
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
