package com.github.mengweijin.vitality.framework.mybatis.data.permission;

import com.github.mengweijin.vitality.framework.mybatis.consts.ColumnConst;
import lombok.Getter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can only be used in *Mapper.java. More detail refer to {@link BaseDataPermissionHandler}
 * @author mengweijin
 * @since 2022/11/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {

    String tableAlias() default "";

    /**
     * For Examples:
     * DEPT: dept_id
     * ROLE: role_id
     * USER: create_by
     * */
    String tableColumnName() default "";

    Scope scope() default Scope.USER;

    @Getter
    enum Scope {

        USER(ColumnConst.CREATE_BY),

        DEPT(ColumnConst.DEPT_ID),

        ROLE(ColumnConst.ROLE_ID);

        private String columnName;

        Scope(String columnName) {

        }
    }
}
