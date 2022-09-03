package com.github.mengweijin.generator.util;

import cn.hutool.core.util.ClassUtil;

import java.util.Arrays;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
public final class Utils {

    private Utils() {}

    public static Class<Object> getMainClass() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            StackTraceElement[] stackTraceArray = e.getStackTrace();
            StackTraceElement mainElement = Arrays.stream(stackTraceArray).filter(element -> "main".equals(element.getMethodName())).findFirst().get();
            // com.github.mengweijin.generator.DefaultGenerator
            String className = mainElement.getClassName();
            return ClassUtil.loadClass(className, false);
        }
    }

    public static String getMainClassPackage() {
        return ClassUtil.getPackage(getMainClass());
    }
}
