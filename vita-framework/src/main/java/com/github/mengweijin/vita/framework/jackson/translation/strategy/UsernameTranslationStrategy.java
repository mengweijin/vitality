package com.github.mengweijin.vita.framework.jackson.translation.strategy;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Component;

/**
 * 用户名翻译
 * @author mengweijin
 * @since 2023/5/20
 */
@Component
@AllArgsConstructor
public class UsernameTranslationStrategy implements ITranslationStrategy<String> {

    private UserService userService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.USER_ID_TO_USERNAME;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof Long id) {
            return userService.getUsernameById(id);
        } else if(value instanceof String ids && StrUtil.isNotBlank(ids)) {
            return userService.getUsernameByIds(ids);
        }
        return null;
    }

}
