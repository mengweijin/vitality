package com.github.mengweijin.vitality.framework.jackson.translation.strategy;

import com.github.mengweijin.vitality.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vitality.framework.jackson.translation.Translation;
import com.github.mengweijin.vitality.system.service.UserService;
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
public class UserNicknameTranslationStrategy implements ITranslationStrategy<String> {

    private UserService userService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.USER_ID_TO_NICKNAME;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof Long id) {
            return userService.getUserNicknameById(id);
        } else if(value instanceof String ids && StrUtil.isNotBlank(ids)) {
            return userService.getUserNicknameByIds(ids);
        }
        return null;
    }
}
