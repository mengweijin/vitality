package com.github.mengweijin.vitality.framework.validator;


import com.github.mengweijin.vitality.framework.validator.annotation.BusinessCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 自定义业务校验注解实现
 * @author mengweijin
 */
public class BusinessCheckValidator implements ConstraintValidator<BusinessCheck, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private Class<? extends BusinessCheckValidator.BusinessCheckRule> clazz;

    @Override
    public void initialize(BusinessCheck parameters) {
        clazz = parameters.cls();
        validateParameters();
    }
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }
        try {
            BusinessCheckRule businessCheck = clazz.getDeclaredConstructor().newInstance();
            return businessCheck.isValid(value.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateParameters() {
        if (clazz == null) {
            throw LOG.getAnnotationDoesNotContainAParameterException(BusinessCheck.class, "clazz");
        }
    }

    public interface BusinessCheckRule {
        default boolean isValid(String value) {
            return false;
        }
    }
}