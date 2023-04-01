package com.github.mengweijin.vitality.framework.filter.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

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
                // 防xss攻击和过滤前后空格
                escapeValues[i] = Jsoup.clean(values[i], Safelist.relaxed()).trim();
            }
            return escapeValues;
        }
        return super.getParameterValues(name);
    }
}