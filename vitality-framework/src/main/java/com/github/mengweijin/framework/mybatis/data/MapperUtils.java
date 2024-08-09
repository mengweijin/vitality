package com.github.mengweijin.framework.mybatis.data;

import com.github.mengweijin.framework.constant.Const;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author mengweijin
 * @since 2023/5/7
 */
public class MapperUtils {

    public static <T extends Annotation, R> R processMethodExpression(String mappedStatementId, Class<T> annotationClass, Function<T, R> function) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(Const.DOT)));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(Const.DOT) + 1);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                T annotation = method.getAnnotation(annotationClass);
                if (annotation != null && method.getName().equals(methodName)) {
                    return function.apply(annotation);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
