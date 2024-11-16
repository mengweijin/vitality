package com.github.mengweijin.vitality.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EMessageTemplate {

    USER_PASSWORD_EXPIRE("用户密码过期提醒", "您的密码还有【{}天】即将过期，请及时修改！");

    private final String title;

    private final String content;

}
