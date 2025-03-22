package com.github.mengweijin.vitality.system.validator.rule;

import com.github.mengweijin.vitality.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vitality.system.service.ConfigService;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 */
public class    CaptchaCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        ConfigService configService = SpringUtil.getBean(ConfigService.class);
        if(configService.getCaptchaEnabled()) {
            return StrValidator.isNotBlank(value);
        }
        return true;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The captcha code must not be empty!");
    }

}
