package com.github.mengweijin.quickboot.mybatis;

import cn.hutool.core.lang.func.LambdaUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.mybatis.entity.BaseCreatorEntity;
import org.apache.ibatis.reflection.MetaObject;
import java.time.LocalDateTime;

/**
 * 自动填充
 *
 * @author mengweijin
 */
public class BaseCreatorEntityMetaObjectHandler implements MetaObjectHandler {

    /**
     * For Example:  "SESSION_USER"
     * ServletUtils.SESSION_USER
     */
    private String sessionUserName;

    public BaseCreatorEntityMetaObjectHandler(String sessionUserName) {
        this.sessionUserName = sessionUserName;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        String user = ServletUtils.getSession().getAttribute(sessionUserName).toString();
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getCreateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getCreateBy), String.class, user);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getUpdateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getUpdateBy), String.class, user);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String user = ServletUtils.getSession().getAttribute(sessionUserName).toString();
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getUpdateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseCreatorEntity::getUpdateBy), String.class, user);
    }
}
