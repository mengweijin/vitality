package com.github.mengweijin.framework.jackson.translation.strategy;

import com.github.mengweijin.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.framework.jackson.translation.Translation;
import com.github.mengweijin.system.service.DictDataService;
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
public class DictDataLabelTranslationStrategy implements ITranslationStrategy<String> {

    private DictDataService dictDataService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DICT_DATA_TO_LABEL;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof String dictValue && StrUtil.isNotBlank(dictValue)) {
            return dictDataService.getLabelByDictTypeCodeAndDictDataCode(translation.dictType(), dictValue);
        }
        return null;
    }

}
