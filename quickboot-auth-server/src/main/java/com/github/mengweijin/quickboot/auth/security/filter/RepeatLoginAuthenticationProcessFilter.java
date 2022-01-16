package com.github.mengweijin.quickboot.auth.security.filter;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.auth.system.service.TokenService;
import com.github.mengweijin.quickboot.auth.utils.TokenUtils;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重复登录处理过滤器
 * 避免在 Redis 中存在同一个用户有多个 token 的登录记录
 * Spring Security 默认的表单登录认证的过滤器是 {@link UsernamePasswordAuthenticationFilter }，
 * @author mengweijin
 * @date 2022/1/8
 */
@Slf4j
@Component
public class RepeatLoginAuthenticationProcessFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 跳过除了登录 url 以外的所有url, 即只有当前请求的 url 为 /login 才需要执行 doFilterInternal 方法。与 TokenVerifyFilter 相反
        return !TokenVerifyFilter.LOGIN_URL.equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = ServletUtils.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, Const.EMPTY);
        final String uuid = redisCache.getCacheObject(TokenUtils.LOGIN_USER_KEY + username);
        if(StrUtil.isNotEmpty(uuid)) {
            log.warn("User {} repeat login.", username);
            tokenService.deleteToken(username, uuid);
        }

        filterChain.doFilter(request, response);
    }
}
