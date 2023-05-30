package com.github.mengweijin.vitality.framework.util;

import com.github.mengweijin.vitality.framework.constant.Const;
import org.dromara.hutool.core.text.CharSequenceUtil;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
public class SpringDocUtils {

    /**
     * com.github.mengweijin.vitality.framework.doc
     */
    public static <E> String getPackagePath(Class<E> cls) {
        return cls.getPackage().getName();
    }

    public static <E> String getParentPackagePath(Class<E> cls) {
        return CharSequenceUtil.subBefore(getPackagePath(cls), '.', true);
    }

    /**
     * 以最后一个包名作为 Group 名称，首字母大写然后拼接 + “ APIs"。例如："Doc APIs"
     * @param packagePath com.github.mengweijin.vitality.framework.doc
     * @return doc
     */
    public static String createGroupName(String packagePath) {
        String packageName;
        if(CharSequenceUtil.contains(packagePath, Const.DOT)) {
            packageName = CharSequenceUtil.subAfter(packagePath, Const.DOT, true);
        } else {
            packageName = packagePath;
        }

        return CharSequenceUtil.upperFirst(packageName) + " APIs";
    }
}
