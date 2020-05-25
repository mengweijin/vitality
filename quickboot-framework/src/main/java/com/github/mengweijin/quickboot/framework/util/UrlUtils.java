package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.util.StrUtil;

/**
 * @author mengweijin
 */
public class UrlUtils {

    public static boolean isUrl(String str) {
        if (StrUtil.isBlank(str)){
            return false;
        } else {
            str = str.trim();
            return str.matches("^(http|https)://.+");
        }
    }

}
