package com.github.mengweijin.framework.validator;


import com.github.mengweijin.framework.validator.annotation.BusinessCheck;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 自定义业务校验注解实现
 *
 * @author mengweijin
 */
public class BusinessCheckValidator implements ConstraintValidator<BusinessCheck, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private Class<? extends BusinessCheckValidator.BusinessCheckRule>[] clazz;

    @Override
    public void initialize(BusinessCheck parameters) {
        clazz = parameters.clazz();
        validateParameters();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        //禁止默认消息返回
        context.disableDefaultConstraintViolation();
        try {
            for (Class<? extends BusinessCheckRule> cls : clazz) {
                BusinessCheckRule businessCheck = cls.getDeclaredConstructor().newInstance();
                boolean valid = businessCheck.isValid(value);
                if (!valid) {
                    //自定义返回消息
                    context.buildConstraintViolationWithTemplate(businessCheck.message()).addConstraintViolation();
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateParameters() {
        if (clazz == null || clazz.length == 0) {
            throw LOG.getAnnotationDoesNotContainAParameterException(BusinessCheck.class, "clazz");
        }
    }

    public interface BusinessCheckRule {

        boolean isValid(CharSequence value);

        String message();
    }
}