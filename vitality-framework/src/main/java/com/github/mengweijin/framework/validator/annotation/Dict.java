package com.github.mengweijin.framework.validator.annotation;

import com.github.mengweijin.framework.validator.DictValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义字典数据校验注解
 * @author mengweijin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {DictValidator.class})
public @interface Dict {

    String message() default "Dict data Check failed!";

    String typeCode() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
