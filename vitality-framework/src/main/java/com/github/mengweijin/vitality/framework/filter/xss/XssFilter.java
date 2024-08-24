package com.github.mengweijin.vitality.framework.filter.xss;

import com.github.mengweijin.vitality.framework.constant.Const;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.regex.ReUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * XSS 攻击过滤器 , 如集成 Spring Security 后就不需要，security 自带 xss 和 csrf 验证
 * @author mengweijin
 **/
public class XssFilter implements Filter {

    public static final String ENABLED_NAME = "enabled";

    public static final String EXCLUDES_NAME = "excludes";

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
        String tempEnabled = filterConfig.getInitParameter(ENABLED_NAME);
        if (CharSequenceUtil.isNotEmpty(tempEnabled)) {
            enabled = Boolean.parseBoolean(tempEnabled);
        }

        String tempExcludes = filterConfig.getInitParameter(EXCLUDES_NAME);
        if (CharSequenceUtil.isNotBlank(tempExcludes)) {
            excludes.addAll(Arrays.asList(tempExcludes.split(Const.COMMA)));
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
        return excludes.stream().anyMatch(pattern -> ReUtil.isMatch(Const.CARET + pattern, request.getServletPath()));
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
