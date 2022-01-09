package com.github.mengweijin.quickboot.auth.security.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.auth.data.entity.User;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.token.TokenUtils;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方工程验证
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserDetailsService userDetailServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if("/login".equals(request.getRequestURI())) {
            // 继续执行下一个过滤器
            chain.doFilter(request, response);
        } else {
            // 获取请求携带的令牌
            String token = request.getHeader(authProperties.getToken().getHeader());
            if(StrUtil.isBlank(token)) {
                // 请求未携带 token
                R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "Please login first!");
                ServletUtils.renderString(response, objectMapper.writeValueAsString(r));
            }  else {
                // 从 token 中获取 uuid
                String uuid = TokenUtils.getUuidFromToken(authProperties.getToken().getSecret(), token);
                // 根据 uuid 从 redis 中查找
                LoginUser loginUser = redisCache.getCacheObject(TokenUtils.LOGIN_TOKEN_KEY + uuid);
                if(loginUser == null) {
                    // token 已过期
                    R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "Token expired! Please login again!");
                    ServletUtils.renderString(response, objectMapper.writeValueAsString(r));
                } else {
                    // 能走到这里redis 中有 user，说明已经登录过了
                    // 刷新 token 过期时间（只要用户有操作，就刷新过期时间，达到自动延长的目的）
                    redisCache.expire(TokenUtils.LOGIN_TOKEN_KEY + uuid, authProperties.getToken().getExpire());

                    if(SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(loginUser.getUsername());
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                    // 继续执行下一个过滤器
                    chain.doFilter(request, response);
                }
            }
        }
    }
}
