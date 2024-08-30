package com.github.mengweijin.vitality.framework.repeatable;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Repeatable 过滤器
 * <p>
 * 使用：
 * if (request instanceof RepeatedlyRequestWrapper wrapper) {
 *    String read = IoUtil.read(wrapper.getInputStream(), StandardCharsets.UTF_8);
 * }
 * @author mengweijin
 */
public class RepeatableFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no need init, do nothing.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest httpServletRequest && CharSequenceUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            requestWrapper = new RepeatedlyRequestWrapper(httpServletRequest, response);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
        // no need destroy operation, do nothing.
    }
}
