package com.github.mengweijin.vitality.system.enums;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum EGender {

    MALE("男"), FEMALE("女"), OTHER("其他");

    private final String value;

    EGender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
