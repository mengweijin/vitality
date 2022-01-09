package com.github.mengweijin.quickboot.auth.security.handler;

import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Spring security 默认的实现是 {@link SimpleUrlAuthenticationFailureHandler} 这个不适合前后端分离项目。
 * 前后端分离不需要处理页面重定向，只需要返回失败的 json
 * 因此，这里自定义一个
 *
 * @author mengweijin
 * @date 2022/1/8
 */
@Slf4j
@Component
public class QuickBootAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * 用户登录失败次数 redis key
     */
    public static final String LOGIN_FAILED_TIMES_KEY = "login_failed_times:";

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private DefaultRedisScript<Long> limitScript;

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 先实现自己的业务逻辑
        String username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        String key = LOGIN_FAILED_TIMES_KEY + username;
        int expireTime = authProperties.getLogin().getExpire();
        int count = authProperties.getLogin().getMaxFailureTimes();
        List<Object> keys = Collections.singletonList(key);
        // 加入登录失败缓存。调用脚本，没有缓存的话就创建并设置过期时间，有的话就自增 1
        Long number = redisTemplate.execute(limitScript, keys, count, expireTime);

        // 异步记录登录失败日志
        loginLogTask.addFailureLoginLog(request, username, exception.getMessage());

        R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        ServletUtils.render(response, r);
    }
}
