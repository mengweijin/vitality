package com.github.mengweijin.quickboot.auth.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类 返回未授权
 * Spring Security 默认使用 {@link LoginUrlAuthenticationEntryPoint} 来处理
 * 当前我们的应用为前后端分离工程，只需要响应 json 就行
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class QuickBootAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        R<Object> r = R.fail(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        ServletUtils.renderString(response, objectMapper.writeValueAsString(r));
    }
}
