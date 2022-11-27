package com.github.mengweijin.vitality.cache;

/**
 * {@link <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache">Spring Cache Documents</a>}
 *
 *
 *
 * KEY_EXPRESSION 为 @Cacheable 中的 key 值，默认使用 SPEL 表达式，若要拼接普通文本，需要用单引号包裹起来。
 *
 * For Example 1: @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS_METHOD_PARAM, unless = "#result?.size() == 0")
 * For Example 2: @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS + "+#username + 'zhangsan'", unless = "#result == null")
 *
 * @author mengweijin
 * @date 2022/9/3
 */
public interface CacheConst {

    String NAME_NEVER_EXPIRE = "cache_never_expire";
    String NAME_7_DAY = "cache_7_day";
    String NAME_1_DAY = "cache_1_day";
    String NAME_12_HOURS = "cache_12_hours";
    String NAME_10_MINUTES = "cache_10_minutes";
    String NAME_1_MINUTES = "cache_1_minutes";

    String KEY_CLASS = "#root.targetClass.name";
    String KEY_CLASS_METHOD = "#root.targetClass.name + ':' + #root.methodName";
    String KEY_ARGS_0_VALUE = " #root.args[0]";

    String UNLESS_LIST = "#result?.size() == 0";
    String UNLESS_OBJECT = "#result == null";
}
