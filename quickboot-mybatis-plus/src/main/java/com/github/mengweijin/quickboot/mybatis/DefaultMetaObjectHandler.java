package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.framework.util.lambda.LambdaWrapper;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * 自动填充
 *
 * @author mengweijin
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    private static final String SYSTEM = "SYSTEM";

    @Override
    public void insertFill(MetaObject metaObject) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getCreateTime), ZonedDateTime.class, utc);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getCreateBy), String.class, SYSTEM);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateTime), ZonedDateTime.class, utc);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateBy), String.class, SYSTEM);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateTime), ZonedDateTime.class, utc);
        this.strictInsertFill(metaObject, LambdaWrapper.getFieldName(BaseEntity::getUpdateBy), String.class, SYSTEM);
    }
}
