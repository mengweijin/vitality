package com.github.mengweijin.quickboot.mybatis;

import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quickboot.util.ServletUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.function.Supplier;

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
     * session/shiro/spring security 等认证框架上下文中存储的当前登录用户变量名
     */
    public static final String LOGIN_USER_NAME = "LOGIN_USER_NAME";

    @Value("${quickboot.mybatis-plus.login-username-key:LOGIN_USER_NAME}")
    private String loginUsernameKey;

    @Override
    public void insertFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateTime), LocalDateTime.class, localDateTime);
            this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);

            // session LOGIN_USER
            String username = this.getUsernameFromSession();
            if(StrUtil.isNotBlank(username)) {
                this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getCreateBy), String.class, username);
                this.strictInsertFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if(originalObject instanceof BaseEntity) {
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictUpdateFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateTime), LocalDateTime.class, localDateTime);

            String username = this.getUsernameFromSession();
            if(StrUtil.isNotBlank(username)) {
                this.strictUpdateFill(metaObject, LambdaUtil.getFieldName(BaseEntity::getUpdateBy), String.class, username);
            }
        }
    }

    /**
     * 填充策略
     * MetaObjectHandler提供的默认方法的策略均为：如果属性有值则不覆盖，如果填充值为null则不填充
     * 这里修改为：一律填充
     */
    @Override
    public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
        metaObject.setValue(fieldName, fieldVal.get());
        return this;
    }

    /**
     * session LOGIN_USER_NAME
     * @return username from session key LOGIN_USER_NAME
     */
    private String getUsernameFromSession(){
        Object username = null;
        if(RequestContextHolder.getRequestAttributes() != null) {
            username = ServletUtils.getSession().getAttribute(loginUsernameKey);
        }
        return username == null ? null : String.valueOf(username);
    }

}
