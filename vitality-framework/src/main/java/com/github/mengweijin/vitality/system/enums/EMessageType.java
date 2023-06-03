package com.github.mengweijin.vitality.system.enums;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum EMessageType {

    NOTICE("通知"), ANNOUNCEMENT("公告"), BACKLOG("待办");

    private final String value;

    EMessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
