package com.github.mengweijin.quickboot.orika.configurer;

import cn.hutool.core.lang.func.LambdaUtil;
import com.github.mengweijin.quickboot.orika.OrikaConverter;
import com.github.mengweijin.quickboot.orika.converter.BeanConverter;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Component
public class BeanAToBConverter extends BeanConverter<BeanA, BeanB> {

    @Override
    public void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
        classMapBuilder.field("nameA", "nameB")
                .fieldMap("stringToDate", "stringToDate").converter(OrikaConverter.DATE_NORM_DATE.name()).add()
                .field("date", LambdaUtil.getFieldName(BeanB::getLocalDate))
                .field("date", LambdaUtil.getFieldName(BeanB::getLocalDateTime));
    }
}
