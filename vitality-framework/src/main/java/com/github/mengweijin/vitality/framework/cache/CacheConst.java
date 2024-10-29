package com.github.mengweijin.vitality.framework.cache;

/**
 * UNLESS 默认不加的好处是，也缓存为空的结果，来避免缓存穿透。
 * @author mengweijin
 * @since 2022/9/3
 */
@SuppressWarnings({"unused"})
public interface CacheConst {

    String KEY_CLASS = "#root.targetClass.name";

    String KEY_CLASS_METHOD = "#root.targetClass.name + ':' + #root.methodName";

    String KEY_ARGS_0_VALUE = " #root.args[0]";

    String KEY_ALL = "'ALL'";

    String UNLESS_LIST_EMPTY = "#result?.size() == 0";

    String UNLESS_OBJECT_NULL = "#result == null";
}
