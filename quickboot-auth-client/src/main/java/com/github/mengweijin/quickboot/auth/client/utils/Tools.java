package com.github.mengweijin.quickboot.auth.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mengweijin
 * @date 2022/1/15
 */
public class Tools {

    public static String getPackage(Class<?> clazz) {
        String empty = "";
        String dot = ".";
        if (clazz == null) {
            return empty;
        }
        final String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf(dot);
        if (packageEndIndex == -1) {
            return empty;
        }
        return className.substring(0, packageEndIndex);
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param object   待渲染的字符串/对象
     */
    public static void render(HttpServletResponse response, Object object) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if(object instanceof R) {
            response.setStatus(((R<?>) object).getCode());
        }
        response.getWriter().print(objectMapper.writeValueAsString(object));
    }
}
