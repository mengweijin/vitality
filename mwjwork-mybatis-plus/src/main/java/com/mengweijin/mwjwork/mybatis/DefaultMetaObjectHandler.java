package com.mengweijin.mwjwork.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mengweijin.mwjwork.common.util.lambda.LambdaWrapper;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 自动填充
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    private static final String SYSTEM = "SYSTEM";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getCreateTime), LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getCreateBy), String.class, SYSTEM);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateBy), String.class, SYSTEM);
    }
}
