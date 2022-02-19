package com.github.mengweijin.quickboot.auth.security.handler;

import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.security.SecurityConst;
import com.github.mengweijin.quickboot.auth.system.service.TokenService;
import com.github.mengweijin.quickboot.auth.utils.TokenUtils;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
@Slf4j
@Component
public class QuickBootAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginLogTask loginLogTask;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // UserDetails 中的 username 属性实际上在 UserDetailsServiceImpl 中存放的是 user 的 id
        String username = userDetails.getUsername();

        // 如果用户已经登录过了，那么删除之前的 token，重新生成一个新的存入 redis。避免在 Redis 中存在同一个用户有多个 token 的登录记录
        LoginUser loginUser = redisCache.getCacheObject(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + username);
        if(loginUser != null) {
            log.warn("User {} repeat login.", username);
            // 移除之前的 token
            redisCache.deleteObject(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + username);
        }

        String token = TokenUtils.createToken(authProperties.getToken().getSecret(), username);

        // 收集权限信息
        List<String> roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        loginUser = new LoginUser().setUsername(username).setRoleList(roleList);

        redisCache.setCacheObject(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + username, loginUser, authProperties.getToken().getExpire(), TimeUnit.SECONDS);

        final R<?> r = R.success(null, Collections.singletonMap("token", token));
        ServletUtils.render(response, r);

        // 异步记录登录成功的日志
        loginLogTask.addSuccessLoginLog(request, username);
    }
}
