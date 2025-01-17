package com.github.mengweijin.vitality.framework.jackson.sensitive;

import com.github.mengweijin.vitality.framework.util.AESUtils;
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
    DEFAULT(s -> {
        if (s == null || s.length() < 3) {
            return MaskingUtil.firstMask(s);
        }
        int partLength = s.length() / 3;
        return StrUtil.replaceByCodePoint(s, partLength, partLength * 2, MaskingManager.DEFAULT_MASK_CHAR);
    }),

    /**
     * 只显示第一个字符
     */
    FIRST_MASK(MaskingUtil::firstMask),

    /**
     * 加密
     */
    ENCRYPT(AESUtils.getAES()::encryptBase64),

    /**
     * 中文名
     */
    CHINESE_NAME(MaskingUtil::chineseName),

    /**
     * 身份证脱敏
     */
    ID_CARD(s -> MaskingUtil.idCardNum(s, 3, 4)),

    /**
     * 固定电话脱敏
     */
    FIXED_PHONE(MaskingUtil::fixedPhone),

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
     * 密码脱敏
     */
    PASSWORD(MaskingUtil::password),

    /**
     * 银行卡
     */
    BANK_CARD(MaskingUtil::bankCard),

    /**
     * 车牌号
     */
    CAR_LICENSE(MaskingUtil::carLicense),

    /**
     * IP 地址
     */
    IP(s -> Validator.isIpv4(s) ? MaskingUtil.ipv4(s) : MaskingUtil.ipv6(s));

    private final Function<String, String> desensitize;

    public Function<String, String> desensitize() {
        return desensitize;
    }

}
