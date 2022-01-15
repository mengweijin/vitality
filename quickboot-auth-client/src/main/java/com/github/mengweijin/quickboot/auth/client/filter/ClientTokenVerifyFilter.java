package com.github.mengweijin.quickboot.auth.client.filter;

import com.github.mengweijin.quickboot.auth.client.utils.R;
import com.github.mengweijin.quickboot.auth.client.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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

    public static final String AUTH_HEADER = "Authorization";

    public static final String TOKEN_VERIFY_URL = "http://localhost:8080/token/verify";

    public static final String AUTH_LOGIN_URL = "http://localhost:8080/login";

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
        String token = request.getHeader(AUTH_HEADER);
        if(token == null) {
            // 请求未携带 token
            R<?> r = R.fail(HttpStatus.UNAUTHORIZED.value(), "No token was found!");
            Tools.render(response, r);
            return;
        }

        String url = UriComponentsBuilder.fromHttpUrl(ClientTokenVerifyFilter.TOKEN_VERIFY_URL)
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
