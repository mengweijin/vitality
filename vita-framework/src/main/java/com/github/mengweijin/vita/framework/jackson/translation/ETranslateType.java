package com.github.mengweijin.vita.framework.jackson.translation;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 翻译类型枚举
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ETranslateType implements IEnum<String> {

    USER_ID_TO_USERNAME("USER_ID_TO_USERNAME"),

    USER_ID_TO_NICKNAME("USER_ID_TO_NICKNAME"),

    USER_ID_TO_AVATAR("USER_ID_TO_AVATAR"),

    DEPT_ID_TO_NAME("DEPT_ID_TO_NAME"),

    DICT_DATA_TO_LABEL("DICT_DATA_TO_LABEL");

    private final String value;

}
