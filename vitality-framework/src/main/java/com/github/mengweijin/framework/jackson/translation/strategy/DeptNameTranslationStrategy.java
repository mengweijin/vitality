package com.github.mengweijin.framework.jackson.translation.strategy;

import com.github.mengweijin.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.framework.jackson.translation.Translation;
import com.github.mengweijin.system.service.DeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 用户名翻译
 * @author mengweijin
 * @since 2023/5/20
 */
@Component
@AllArgsConstructor
public class DeptNameTranslationStrategy implements ITranslationStrategy<String> {

    private DeptService deptService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DEPT_ID_TO_NAME;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof Long id) {
            return deptService.getNameById(id);
        }
        return null;
    }

}
