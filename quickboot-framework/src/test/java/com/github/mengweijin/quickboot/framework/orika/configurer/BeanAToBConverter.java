package com.github.mengweijin.quickboot.framework.orika.configurer;

import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
@Component
public class BeanAToBConverter extends BeanConverter<BeanA, BeanB> {

    @Override
    void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
        classMapBuilder.field("nameA", "nameB");
    }
}
