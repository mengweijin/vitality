package com.github.mengweijin.vitality.framework.validator.annotation;

import com.github.mengweijin.vitality.framework.validator.DictValidator;
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

    String code() default "";

    String message() default "Dict data Check failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
