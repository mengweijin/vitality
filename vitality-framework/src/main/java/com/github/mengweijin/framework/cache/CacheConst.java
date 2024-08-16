package com.github.mengweijin.framework.cache;

/**
 * @author mengweijin
 * @since 2022/9/3
 */
public interface CacheConst {

    String KEY_CLASS = "#root.targetClass.name";
    String KEY_CLASS_METHOD = "#root.targetClass.name + ':' + #root.methodName";
    String KEY_ARGS_0_VALUE = " #root.args[0]";

    String UNLESS_LIST_EMPTY = "#result?.size() == 0";
    String UNLESS_OBJECT_NULL = "#result == null";
}
