package com.github.mengweijin.vita.system.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EMessageCategory implements IEnum<String> {

    /**
     * 来自系统发送的消息
     */
    SYSTEM("SYSTEM"),

    /**
     * 安全
     */
    SECURITY("SECURITY"),

    /**
     * 告警消息
     */
    ALERT("ALERT"),

    /**
     * 来自其它用户的消息
     */
    USER("USER");

    private final String value;

}
