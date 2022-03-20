package com.github.mengweijin.quickboot.auth.security.filter;

import cn.hutool.core.text.CharSequenceUtil;
import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.system.service.TokenService;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 验证
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class TokenVerifyFilter extends OncePerRequestFilter {

    public static final String LOGIN_URL = "/login";

    public static final String VERIFY_URL = "/token/verify";

    @Autowired
    private TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 跳过登录 url 和 token 验证 url
        return LOGIN_URL.equals(request.getRequestURI()) || VERIFY_URL.equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 获取请求携带的令牌
        String token = tokenService.getToken(request);
        if(CharSequenceUtil.isBlank(token)) {
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

        // 走到这里说明 redis 中有 user，说明已经登录过了。刷新一下 token 过期时间，达到自动续约的目的
        tokenService.expireRefresh(loginUser.getUsername());

        if(SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = loginUser.createUserDetails();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 继续执行下一个过滤器
        chain.doFilter(request, response);
    }
}
