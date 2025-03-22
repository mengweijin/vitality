package com.github.mengweijin.vita.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EMessageTemplate {

    USER_PASSWORD_LONG_TIME_NO_CHANGE("【提醒】用户密码长时间未修改", "您已经【{}天】没有修改密码了！"),

    TRIGGER_RATE_LIMIT("【告警】触发限流", "用户【{}】触发【{}】接口【{}】限流告警，请注意系统负载！");

    private final String title;

    private final String content;

}
