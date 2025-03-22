package com.github.mengweijin.vita.framework.constant;

import org.dromara.hutool.core.regex.RegexPool;

/**
 * @author mengweijin
 * @since 2024/8/31
 */
public interface Regex extends RegexPool {

    /**
     * 密码正则（密码格式应为8-18位，并且为数字、字母、符号的至少任意两种的组合）
     */
    String PASSWORD = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\\u4E00-\\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$";

}
