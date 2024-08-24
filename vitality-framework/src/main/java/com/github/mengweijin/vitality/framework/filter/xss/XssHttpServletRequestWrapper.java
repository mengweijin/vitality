package com.github.mengweijin.vitality.framework.filter.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.dromara.hutool.http.html.HtmlFilter;

/**
 * XSS过滤处理
 *
 * @author mengweijin
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * @param request HttpServletRequest
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = HtmlFilter.htmlSpecialChars(values[i]);
            }
            return escapeValues;
        }
        return super.getParameterValues(name);
    }
}