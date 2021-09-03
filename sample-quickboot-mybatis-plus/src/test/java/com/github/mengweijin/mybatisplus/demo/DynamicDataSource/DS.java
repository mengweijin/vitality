package com.github.mengweijin.mybatisplus.demo.DynamicDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Meng Wei Jin
 * @description 自定义多数据源切换注解
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {
    /**
     * 切换数据源名称
     */
    String value() default DynamicDataSourceHolder.MASTER;
}
