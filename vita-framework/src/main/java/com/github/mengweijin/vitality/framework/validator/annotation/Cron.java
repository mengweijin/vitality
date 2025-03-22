package com.github.mengweijin.vitality.framework.validator.annotation;

import com.github.mengweijin.vitality.framework.validator.CronValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * cron 表达式校验注解
 * @author mengweijin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {CronValidator.class})
public @interface Cron {

    String message() default "Invalid cron expression!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
