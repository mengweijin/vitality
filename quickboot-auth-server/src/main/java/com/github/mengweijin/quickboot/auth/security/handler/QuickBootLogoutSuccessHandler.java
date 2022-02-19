package com.github.mengweijin.quickboot.auth.security.handler;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.security.SecurityConst;
import com.github.mengweijin.quickboot.auth.system.service.TokenService;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 获取请求携带的令牌
        String token = tokenService.getToken(request);
        if(StrUtil.isBlank(token)) {
            // 请求未携带 token
            R<?> r = R.error(HttpStatus.BAD_REQUEST.value(), "No token was found!");
            ServletUtils.render(response, r);
            return;
        }

        final LoginUser loginUser = tokenService.getLoginUser(token);
        if(loginUser == null) {
            // token 已过期
            R<?> r = R.error(HttpStatus.UNAUTHORIZED.value(), "Token expired!");
            ServletUtils.render(response, r);
            return;
        }

        redisCache.deleteObject(SecurityConst.REDIS_KEY_LOGIN_USERNAME_TOKEN + loginUser.getUsername());

        ServletUtils.render(response, R.success());

        //开启异步任务，写入登出日志
        loginLogTask.addLogoutLog(request, loginUser.getUsername());
    }
}
