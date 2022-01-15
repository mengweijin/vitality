package com.github.mengweijin.quickboot.auth.security.handler;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.utils.TokenUtils;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Meng Wei Jin
 **/
@Component
public class QuickBootLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取请求携带的令牌
        String token = request.getHeader(authProperties.getToken().getHeader());
        if(StrUtil.isBlank(token)) {
            // 请求未携带 token
            R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "Please login first!");
            ServletUtils.render(response, r);
            return;
        }

        // 从 token 中获取 uuid
        String uuid = TokenUtils.getUuidFromToken(authProperties.getToken().getSecret(), token);
        final LoginUser loginUser = redisCache.getCacheObject(TokenUtils.LOGIN_TOKEN_KEY + uuid);
        if(loginUser == null) {
            // token 已过期
            R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "Token already expired!");
            ServletUtils.render(response, r);
            return;
        }

        // 移除之前的 token
        redisCache.deleteObject(TokenUtils.LOGIN_TOKEN_KEY + uuid);
        // 移除之前的 用户已登录的标记
        redisCache.deleteObject(TokenUtils.LOGIN_USER_KEY + loginUser.getUsername());

        ServletUtils.render(response, R.ok());

        //开启异步任务，写入登出日志
        loginLogTask.addLogoutLog(request, loginUser.getUsername());
    }
}
