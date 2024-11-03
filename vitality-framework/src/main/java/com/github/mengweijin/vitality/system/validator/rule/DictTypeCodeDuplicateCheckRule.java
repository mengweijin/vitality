package com.github.mengweijin.vitality.system.validator.rule;

import com.github.mengweijin.vitality.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vitality.system.domain.entity.DictType;
import com.github.mengweijin.vitality.system.service.DictTypeService;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 */
public class DictTypeCodeDuplicateCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        DictTypeService dictTypeService = SpringUtil.getBean(DictTypeService.class);
        DictType dictType = dictTypeService.getByCode((String) value);
        return dictType == null;
    }

    @Override
    public String message(CharSequence value) {
        return StrUtil.format("The dict type code[{}] already exists!", value);
    }

}
