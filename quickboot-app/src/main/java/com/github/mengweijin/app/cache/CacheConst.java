package com.github.mengweijin.app.cache;

/**
 * KEY_EXPRESSION 为 @Cacheable 中的 key 值，默认使用 SPEL 表达式，若要拼接普通文本，需要用单引号包裹起来。
 *
 * @author mengweijin
 * @date 2022/9/3
 */
public interface CacheConst {

    String NAME_DEFAULT = "default";

    String KEY_EXPRESSION_CLASS = "#root.targetClass.simpleName";

    String KEY_EXPRESSION_CLASS_METHOD = "#root.targetClass.simpleName+':'+#root.methodName";

    String KEY_EXPRESSION_CLASS_METHOD_PARAM = "#root.targetClass.simpleName+':'+#root.methodName+':'+#param";
}
