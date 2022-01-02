package com.github.mengweijin.quickboot.mybatis;

import cn.hutool.core.lang.func.LambdaUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import java.time.LocalDateTime;

/**
 * 自动填充
 *
 * @author mengweijin
 */
public class BaseEntityMetaObjectHandler implements MetaObjectHandler {

    /**
     * For Example:  "SESSION_USER"
     * ServletUtils.SESSION_USER
     */
    private String sessionUserName;

    public BaseEntityMetaObjectHandler(String sessionUserName) {
        this.sessionUserName = sessionUserName;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        String username = String.valueOf(ServletUtils.getSession().getAttribute(sessionUserName));
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateBy), String.class, username);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String username = String.valueOf(ServletUtils.getSession().getAttribute(sessionUserName));
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
    }
}
