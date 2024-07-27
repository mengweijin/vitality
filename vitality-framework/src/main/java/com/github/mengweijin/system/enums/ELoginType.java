package com.github.mengweijin.system.enums;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum ELoginType {

    LOGIN("LOGIN"), LOGOUT("LOGOUT"), KICK_OUT("KICK_OUT"), REPLACED("REPLACED");

    private final String value;

    ELoginType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
