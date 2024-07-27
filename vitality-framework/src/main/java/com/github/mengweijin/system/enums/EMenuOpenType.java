package com.github.mengweijin.system.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum EMenuOpenType {

    IFRAME("_iframe"), BLANK("_blank");

    @EnumValue
    @JsonValue
    private final String value;

    EMenuOpenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
