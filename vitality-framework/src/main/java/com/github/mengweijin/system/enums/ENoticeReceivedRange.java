package com.github.mengweijin.system.enums;

/**
 * @author mengweijin
 * @date 2023/5/20
 */
public enum ENoticeReceivedRange {

    ALL_USER("全部用户"),
    SPECIFIED_USER("指定用户"),
    DEPT_USER("指定部门"),
    ROLE_USER("指定角色"),
    POST_USER("指定岗位");

    private final String value;

    ENoticeReceivedRange(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
