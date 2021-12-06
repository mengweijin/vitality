package com.github.mengweijin.quickboot.framework.util.lambda;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * 根据Getter/Setter方法名获取属性
 * <p>
 * Deprecated. Can be instead of LambdaWrapper by cn.hutool.core.lang.func.LambdaUtil.java
 *
 * @author mengweijin
 */
@Slf4j
@Deprecated
public class LambdaWrapper {

    private static final String GET = "get";

    private static final String SET = "set";

    private static final String IS = "is";

    /**
     * 根据getter方法名获取属性名
     *
     * @param function getter function
     * @param <T> t
     * @return 属性名
     */
    public static <T> String getFieldName(IGetterFunction<T> function) {
        SerializedLambda serializedLambda = getSerializedLambda(function);
        return resolveGetterFieldName(serializedLambda.getImplMethodName());
    }

    /**
     * 根据setter方法名获取属性名
     *
     * @param function setter function
     * @param <T> t
     * @return 属性名
     */
    public static <T, U> String getFieldName(ISetterFunction<T, U> function) {
        SerializedLambda serializedLambda = getSerializedLambda(function);
        return resolveSetterFieldName(serializedLambda.getImplMethodName());
    }

    /**
     * 获取 SerializedLambda
     *
     * @param function getter/setter function
     * @return SerializedLambda
     */
    private static SerializedLambda getSerializedLambda(Serializable function) {
        try {
            Method writeReplace = function.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object sl = writeReplace.invoke(function);
            return (SerializedLambda) sl;
        } catch (Exception  e) {
            log.error("Get SerializedLambda error!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理getter方法名
     *
     * @param getMethodName get Method Name
     * @return field name
     */
    public static String resolveGetterFieldName(String getMethodName) {
        if (getMethodName.startsWith(GET)) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith(IS)) {
            getMethodName = getMethodName.substring(2);
        }
        return firstToLowerCase(getMethodName);
    }

    /**
     * 处理setter方法名
     *
     * @param setMethodName set Method Name
     * @return field name
     */
    public static String resolveSetterFieldName(String setMethodName) {
        if (setMethodName.startsWith(SET)) {
            setMethodName = setMethodName.substring(3);
        }
        return firstToLowerCase(setMethodName);
    }

    /**
     * 首字母转换小写
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String firstToLowerCase(String param) {
        if (StringUtils.isBlank(param)) {
            return param;
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }
}
