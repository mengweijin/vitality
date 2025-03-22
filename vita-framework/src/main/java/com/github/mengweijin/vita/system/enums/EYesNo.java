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
public enum EYesNo implements IEnum<String> {

    Y("Y"),

    N("N");

    private final String value;
}
