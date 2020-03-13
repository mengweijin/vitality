package com.mengweijin.mwjwork.framework.validator.annotation;

import com.mengweijin.mwjwork.framework.validator.YesNoValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Meng Wei Jin
 * @description 自定义字段验证
 * @date Create in 2019-10-21 19:58
 **/
@Documented
@Constraint(validatedBy = YesNoValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface YesNo {

    String message() default "${validatedValue}: must match Y or N";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
