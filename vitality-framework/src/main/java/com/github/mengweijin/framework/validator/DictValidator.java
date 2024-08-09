package com.github.mengweijin.framework.validator;

import com.github.mengweijin.framework.validator.annotation.Dict;
import com.github.mengweijin.system.domain.entity.DictData;
import com.github.mengweijin.system.service.DictDataService;
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

    private String typeCode;

    @Override
    public void initialize(Dict parameters) {
        typeCode = parameters.typeCode();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        //禁止默认消息返回
        context.disableDefaultConstraintViolation();

        DictDataService dictDataService = SpringUtil.getBean(DictDataService.class);
        List<DictData> dictDataList = dictDataService.getByTypeCode(typeCode);
        if(CollUtil.isEmpty(dictDataList)) {
            //自定义返回消息
            context.buildConstraintViolationWithTemplate("No dict data was found by dict typeCode=" + typeCode).addConstraintViolation();
            return false;
        }

        boolean anyMatch = dictDataList.stream().map(DictData::getDataCode).anyMatch(item -> item.equals(value.toString()));
        if(!anyMatch) {
            String correctDictDataCode = dictDataList.stream().map(DictData::getDataCode).collect(Collectors.joining());
            String message = StrUtil.format("The dict typeCode[{}] of dataCode[{}] is incorrect! The correct dataCode should be in [{}]", typeCode, value, correctDictDataCode);
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        return true;
    }

    private void validateParameters() {
        if (StrUtil.isBlankOrUndefined(typeCode)) {
            throw LOG.getAnnotationDoesNotContainAParameterException(Dict.class, "typeCode");
        }
    }

}