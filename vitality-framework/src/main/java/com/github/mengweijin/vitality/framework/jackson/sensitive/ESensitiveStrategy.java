package com.github.mengweijin.vitality.framework.jackson.sensitive;

import lombok.AllArgsConstructor;
import org.dromara.hutool.core.data.MaskingUtil;

import java.util.function.Function;

/**
 * 脱敏策略
 *
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@AllArgsConstructor
public enum ESensitiveStrategy {

    /**
     * 默认
     */
    DEFAULT(s -> "********"),

    /**
     * 中文名
     */
    CHINESE_NAME(MaskingUtil::chineseName),

    /**
     * 身份证脱敏
     */
    ID_CARD(s -> MaskingUtil.idCardNum(s, 3, 4)),

    /**
     * 手机号脱敏
     */
    MOBILE_PHONE(MaskingUtil::mobilePhone),

    /**
     * 地址脱敏
     */
    ADDRESS(s -> MaskingUtil.address(s, 8)),

    /**
     * 邮箱脱敏
     */
    EMAIL(MaskingUtil::email),

    /**
     * 银行卡
     */
    BANK_CARD(MaskingUtil::bankCard),

    /**
     * IPv4地址
     */
    IPV4(MaskingUtil::ipv4),

    /**
     * IPv6地址
     */
    IPV6(MaskingUtil::ipv6);

    private final Function<String, String> desensitize;

    public Function<String, String> desensitize() {
        return desensitize;
    }

}
