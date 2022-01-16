package com.github.mengweijin.quickboot.auth.client.filter;

import com.github.mengweijin.quickboot.auth.client.utils.R;
import com.github.mengweijin.quickboot.auth.client.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 第三方工程验证
 * @author mengweijin
 * @date 2022/1/8
 */
@Component
public class ClientTokenVerifyFilter extends OncePerRequestFilter {

    public static final String LOGIN_URL = "/login";

    @Autowired
    private AuthClientProperties authClientProperties;

    /**
     * QuickBootAuthClientAutoConfiguration 中 @Bean 实例化了 RestTemplate，同时又注入了 ClientTokenVerifyFilter 类
     * 而在 ClientTokenVerifyFilter 中又注入了 RestTemplate，形成了循环依赖
     * 在 Spring 2.6 版本以后，默认禁止了循环依赖，启动就会报错。@Lazy 可以解决这个问题。
     */
    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 跳过登录 url
        return LOGIN_URL.equals(request.getRequestURI());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 获取请求携带的令牌
        String token = request.getHeader(authClientProperties.getHeader());
        if(token == null) {
            // 请求未携带 token
            R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "No token was found!");
            Tools.render(response, r);
            return;
        }

        String url = UriComponentsBuilder.fromHttpUrl(authClientProperties.getVerifyUrl())
                .queryParam("token", token)
                .build().encode(StandardCharsets.UTF_8).toString();
        final Boolean isValid = restTemplate.getForObject(url, Boolean.class);

        if(isValid != null && isValid) {
            // 继续执行下一个过滤器
            chain.doFilter(request, response);
        } else {
            Tools.render(response, R.fail(HttpStatus.UNAUTHORIZED.value(), "Invalid token!"));
        }

    }
}
