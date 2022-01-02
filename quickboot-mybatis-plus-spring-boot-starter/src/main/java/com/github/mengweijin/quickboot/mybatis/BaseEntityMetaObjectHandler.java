package com.github.mengweijin.quickboot.mybatis;

import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.web.context.request.RequestContextHolder;
import java.time.LocalDateTime;

/**
 * 自动填充。
 * 注意：如果使用了异步注解或者线程池，而又在新启的线程中用 SpringUtils.getBean(Class) 的方式调用了 mybatis-plus 的 save 方法，
 * 此时，会触发自动填充，而 HttpServletRequest 对象不能跨越线程传递，因此 RequestContextHolder.getRequestAttributes() 总返回 null.
 * 于是，下面的填充 username 的时候就报空指针的错误了。
 *
 * 解决方案：
 * 当使用异步注解或线程池时，不要让 mybatis-plus 触发用户名的自动填充。
 * 此时，如果还需要填充 createBy 和 updateBy 字段，则需要开发者手动给 entity 设置 createBy 和 updateBy 属性的值。
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
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateTime), LocalDateTime.class, localDateTime);
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);

        // 不让 mybatis-plus 触发用户名的自动填充。此时，需要开发者手动给 entity 设置 createBy 和 updateBy 属性的值。
        if(RequestContextHolder.getRequestAttributes() != null) {
            String username = String.valueOf(ServletUtils.getSession().getAttribute(sessionUserName));
            if(this.hasCreateByFiled(metaObject.getOriginalObject())) {
                this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateBy), String.class, username);
            }
            if(this.hasUpdateByFiled(metaObject.getOriginalObject())) {
                this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);

        if(RequestContextHolder.getRequestAttributes() != null) {
            String username = String.valueOf(ServletUtils.getSession().getAttribute(sessionUserName));
            if(this.hasUpdateByFiled(metaObject.getOriginalObject())) {
                this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
            }
        }
    }

    private boolean hasCreateByFiled(Object originalObject) {
        return ReflectUtil.hasField(originalObject.getClass(), LambdaUtil.getFieldName(BaseEntity::getCreateBy));
    }

    private boolean hasUpdateByFiled(Object originalObject) {
        return ReflectUtil.hasField(originalObject.getClass(), LambdaUtil.getFieldName(BaseEntity::getUpdateBy));
    }

}
