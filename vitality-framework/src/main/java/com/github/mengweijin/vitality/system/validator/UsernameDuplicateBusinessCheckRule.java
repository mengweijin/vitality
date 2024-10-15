package com.github.mengweijin.vitality.system.validator;

import com.github.mengweijin.vitality.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.service.UserService;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 * @since 2024/10/15
 */
public class UsernameDuplicateBusinessCheckRule implements BusinessCheckValidator.BusinessCheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        UserService userService = SpringUtil.getBean(UserService.class);
        User user = userService.getByUsername((String) value);
        return user == null;
    }

    @Override
    public String message() {
        return "";
    }
}
