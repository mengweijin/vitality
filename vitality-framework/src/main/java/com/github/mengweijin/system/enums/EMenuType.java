package com.github.mengweijin.system.enums;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum EMenuType {

    DIR(0), MENU(1), BTN(2), OTHER(3);

    private final Integer value;

    EMenuType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
