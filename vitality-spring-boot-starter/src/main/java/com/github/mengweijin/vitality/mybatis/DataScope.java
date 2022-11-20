package com.github.mengweijin.vitality.mybatis;

import lombok.Getter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mengweijin
 * @date 2022/11/20
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
    String tableColumn() default "";

    Scope scope() default Scope.ALL;

    @Getter
    enum Scope {

        DEPT("dept_id"),

        ROLE("role_id"),

        USER("create_by"),

        ALL(null);


        private String defaultColumn;

        Scope(String defaultColumn) {

        }
    }
}
