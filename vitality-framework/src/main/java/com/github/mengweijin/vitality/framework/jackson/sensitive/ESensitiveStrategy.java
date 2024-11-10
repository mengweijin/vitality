package com.github.mengweijin.vitality.framework.jackson.sensitive;

import lombok.AllArgsConstructor;
import org.dromara.hutool.core.data.MaskingUtil;
import org.dromara.hutool.core.data.masking.MaskingManager;
import org.dromara.hutool.core.lang.Validator;
import org.dromara.hutool.core.text.StrUtil;

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
     * TOKEN
     */
    TOKEN(s -> StrUtil.replaceByCodePoint(s, 4, 28, MaskingManager.DEFAULT_MASK_CHAR)),

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

    IP(s -> Validator.isIpv4(s) ? MaskingUtil.ipv4(s) : MaskingUtil.ipv6(s)),

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
