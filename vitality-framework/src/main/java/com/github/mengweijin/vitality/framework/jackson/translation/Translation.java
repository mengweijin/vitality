package com.github.mengweijin.vitality.framework.jackson.translation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通用翻译注解
 *
 * @author mengweijin
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = TranslationSerializer.class)
public @interface Translation {

    /**
     * 翻译类型
     */
    ETranslateType translateType();

    /**
     * 映射字段 (如果不为空则取此字段的值)
     */
    String field() default "";

    /**
     * 字典类型编码 (sys_user_sex)
     */
    String dictCode() default "";

}
