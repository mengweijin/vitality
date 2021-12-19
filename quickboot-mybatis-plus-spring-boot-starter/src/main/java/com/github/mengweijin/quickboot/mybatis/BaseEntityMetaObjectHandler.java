package com.github.mengweijin.quickboot.mybatis;

import cn.hutool.core.lang.func.LambdaUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import java.time.LocalDateTime;

/**
 * 自动填充
 *
 * @author mengweijin
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);
    }
}
