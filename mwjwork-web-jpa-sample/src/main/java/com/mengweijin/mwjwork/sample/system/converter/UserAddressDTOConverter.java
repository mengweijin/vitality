package com.mengweijin.mwjwork.sample.system.converter;

import com.mengweijin.mwjwork.common.util.lambda.LambdaWrapper;
import com.mengweijin.mwjwork.framework.orika.BeanConverterRegister;
import com.mengweijin.mwjwork.sample.system.dto.UserAddressDTO;
import com.mengweijin.mwjwork.sample.system.entity.User;
import ma.glasnost.orika.metadata.ClassMapBuilder;

public class UserAddressDTOConverter implements BeanConverterRegister<UserAddressDTO, User> {

    @Override
    public void fieldMapping(ClassMapBuilder<UserAddressDTO, User> classMapBuilder) {
        classMapBuilder
                .field(LambdaWrapper.getFieldName(UserAddressDTO::getName), LambdaWrapper.getFieldName(User::getName))
                .field(LambdaWrapper.getFieldName(UserAddressDTO::getAge), LambdaWrapper.getFieldName(User::getAge))
                .field(LambdaWrapper.getFieldName(UserAddressDTO::getRole), LambdaWrapper.getFieldName(User::getRole))
        ;
    }
}
