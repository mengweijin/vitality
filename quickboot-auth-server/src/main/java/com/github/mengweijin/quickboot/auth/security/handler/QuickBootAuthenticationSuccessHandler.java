package com.github.mengweijin.quickboot.auth.security.handler;

import cn.hutool.core.util.IdUtil;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.data.entity.User;
import com.github.mengweijin.quickboot.auth.data.service.UserService;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.token.TokenUtils;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 在 spring security 主流程 {@link AbstractAuthenticationProcessingFilter } 中的 doFilter 会处理认证成功，认证失败的逻辑。
 * 在这个类中，我们可以发现成功和失败的默认处理 Handler 为：
 * 	private {@link AuthenticationSuccessHandler} successHandler = new {@link SavedRequestAwareAuthenticationSuccessHandler}();
 * 	private {@link AuthenticationFailureHandler} failureHandler = new {@link SimpleUrlAuthenticationFailureHandler}();
 *
 * 	而在 {@link WebSecurityConfigurerAdapter } 类中，是可以自己指定的，如下：
 * 	http.formLogin().successHandler(successHandler).failureHandler(failureHandler).permitAll();
 *
 * 	那么我们要扩展的话，就可以自己实现一个类，加入自己的业务逻辑。（因为前后端分离不需要跳转 url，只需要把 token 响应给调用者）
 *
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class QuickBootAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLogTask loginLogTask;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 先实现自己的业务逻辑，后面的请求验证时会用到
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.getByUsername(username);
        LoginUser loginUser = new LoginUser().convert(user);

        String uuid = IdUtil.fastUUID();
        redisCache.setCacheObject(TokenUtils.LOGIN_TOKEN_KEY + uuid, loginUser, authProperties.getToken().getExpire(), TimeUnit.SECONDS);


        // 异步记录登录成功的日志
        loginLogTask.addSuccessLoginLog(request, username);

        String token = TokenUtils.createToken(authProperties.getToken().getSecret(), uuid);

        final R<?> r = R.ok(Collections.singletonMap("token", token));
        ServletUtils.render(response, r);
    }
}
