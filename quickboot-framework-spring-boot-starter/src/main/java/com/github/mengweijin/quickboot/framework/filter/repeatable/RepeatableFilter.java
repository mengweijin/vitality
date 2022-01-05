package com.github.mengweijin.quickboot.framework.filter.repeatable;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.MediaType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Repeatable 过滤器
 *
 * 使用：
 * if (request instanceof RepeatedlyRequestWrapper) {
 *    RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
 *    String read = IoUtil.read(requestWrapper.getInputStream(), StandardCharsets.UTF_8);
 * }
 * 借鉴 ruoyi-Vue
 * @author mengweijin
 */
public class RepeatableFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest && StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
