package com.github.mengweijin.quickboot.auth.security.filter;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.framework.util.Const;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security 默认的表单登录认证的过滤器是 {@link UsernamePasswordAuthenticationFilter }，
 * 这个过滤器并不适用于前后端分离的架构，因此我们需要自定义一个过滤器。（前后端分离不需要跳转 url，只需要把 token 响应回去）
 * 逻辑很简单，参照 {@link UsernamePasswordAuthenticationFilter } 这个过滤器改造一下
 * @author mengweijin
 * @date 2022/1/8
 */
//@Component
public class JwtLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);

        username = StrUtil.blankToDefault(username, Const.EMPTY).trim();
        password = StrUtil.blankToDefault(password, Const.EMPTY).trim();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return this.getAuthenticationManager().authenticate(authenticationToken);

    }
}
