package com.github.mengweijin.vitality.framework.validator;

import com.github.mengweijin.vitality.framework.validator.annotation.Dict;
import com.github.mengweijin.vitality.system.domain.entity.DictData;
import com.github.mengweijin.vitality.system.service.DictDataService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义典数据校验注解实现
 * @author mengweijin
 */
public class DictValidator implements ConstraintValidator<Dict, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private String code;

    @Override
    public void initialize(Dict parameters) {
        code = parameters.code();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        DictDataService dictDataService = SpringUtil.getBean(DictDataService.class);
        List<DictData> dictDataList = dictDataService.getByCode(code);
        if(CollUtil.isEmpty(dictDataList)) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            //自定义返回消息
            context.buildConstraintViolationWithTemplate("No dict data was found by dict code=" + code).addConstraintViolation();
            return false;
        }

        boolean anyMatch = dictDataList.stream().map(DictData::getVal).anyMatch(item -> item.equals(value.toString()));
        if(!anyMatch) {
            //禁止默认消息返回
            context.disableDefaultConstraintViolation();
            String correctDictDataCode = dictDataList.stream().map(DictData::getVal).collect(Collectors.joining());
            String message = StrUtil.format("The dict_data_code[{}] of dict_type_code[{}] is incorrect! The correct dict_data_code should be in [{}]", value, code, correctDictDataCode);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

    private void validateParameters() {
        if (StrUtil.isBlankOrUndefined(code)) {
            throw LOG.getAnnotationDoesNotContainAParameterException(Dict.class, "code");
        }
    }

}