package com.mengweijin.mwjwork.framework.orika;

import ma.glasnost.orika.metadata.ClassMapBuilder;

public class BeanAToBConverter implements BeanConverterRegister<BeanA, BeanB> {

    @Override
    public void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
        classMapBuilder.field("nameA", "nameB");
    }
}
