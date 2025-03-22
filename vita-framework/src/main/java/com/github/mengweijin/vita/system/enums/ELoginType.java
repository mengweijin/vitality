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
public enum ELoginType implements IEnum<String> {

    LOGIN("LOGIN"),

    LOGOUT("LOGOUT"),

    KICK_OUT("KICK_OUT"),

    REPLACED("REPLACED");

    private final String value;

}
