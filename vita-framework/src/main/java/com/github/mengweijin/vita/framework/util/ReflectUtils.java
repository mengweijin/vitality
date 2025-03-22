package com.github.mengweijin.vita.framework.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.reflect.ReflectUtil;
import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.core.text.StrUtil;

import java.lang.reflect.Method;

/**
 * 反射工具类. 提供调用getter/setter方法, 访问私有变量, 调用私有方法, 获取泛型类型Class, 被AOP过的真实类等工具函数.
 * @author mengweijin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectUtils extends ReflectUtil {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    /**
     * 调用Getter方法.
     * 支持多级，如：对象名.对象名.方法
     */
    @SuppressWarnings("unchecked")
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : propertyName.split("\\.")) {
            object = MethodUtil.invoke(object, StrUtil.upperFirstAndAddPre(name, GETTER_PREFIX));
        }
        return (E) object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名。
     * 支持多级，如：对象名.对象名.方法
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        Object object = obj;
        String[] names = propertyName.split("\\.");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = StrUtil.upperFirstAndAddPre(names[i], GETTER_PREFIX);
                object = MethodUtil.invoke(object, getterMethodName);
            } else {
                String setterMethodName = StrUtil.upperFirstAndAddPre(names[i], SETTER_PREFIX);
                Method method = MethodUtil.getMethodByName(object.getClass(), setterMethodName);
                MethodUtil.invoke(object, method, value);
            }
        }
    }

}
