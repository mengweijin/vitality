package com.github.mengweijin.vita.system.validator.rule;

import com.github.mengweijin.vita.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vita.system.domain.entity.User;
import com.github.mengweijin.vita.system.service.UserService;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 */
public class UsernameDuplicateCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        UserService userService = SpringUtil.getBean(UserService.class);
        User user = userService.getByUsername((String) value);
        return user == null;
    }

    @Override
    public String message(CharSequence value) {
        return StrUtil.format("The username[{}] already exists!", value);
    }

}
