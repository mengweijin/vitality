package com.github.mengweijin.app.videodownloader.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mengweijin
 */
@Documented
@Constraint(validatedBy = VideoUrlValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VideoUrlValidate {

    String message() default "${validatedValue}: 不支持当前视频地址. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
