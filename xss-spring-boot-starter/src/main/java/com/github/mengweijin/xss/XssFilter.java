package com.github.mengweijin.xss;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * XSS 攻击过滤器
 **/
public class XssFilter implements Filter {

    public static final String ENABLED = "enabled";

    public static final String EXCLUDES = "excludes";

    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();

    /**
     * xss过滤开关
     */
    public boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) {
        String tempEnabled = filterConfig.getInitParameter(ENABLED);
        if (StrUtil.isNotEmpty(tempEnabled)) {
            enabled = Boolean.parseBoolean(tempEnabled);
        }

        String tempExcludes = filterConfig.getInitParameter(EXCLUDES);
        if (StrUtil.isNotBlank(tempExcludes)) {
            excludes.addAll(Arrays.asList(tempExcludes.split(XssFilterAutoConfiguration.COMMA)));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (enabled && !isExcludeUrl(httpServletRequest)) {
            request = new XssHttpServletRequestWrapper(httpServletRequest);
        }
        chain.doFilter(request, response);
    }

    private boolean isExcludeUrl(HttpServletRequest request) {
        return excludes.stream().anyMatch(pattern -> ReUtil.isMatch(XssFilterAutoConfiguration.CARET + pattern, request.getServletPath()));
    }

    @Override
    public void destroy() {

    }
}
