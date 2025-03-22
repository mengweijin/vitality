package com.github.mengweijin.vita.framework.validator;

import com.github.mengweijin.vita.framework.validator.annotation.Cron;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.scheduling.support.CronExpression;

/**
 * cron 表达式校验注解实现
 * @author mengweijin
 */
public class CronValidator implements ConstraintValidator<Cron, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return CronExpression.isValidExpression(value);
    }
}