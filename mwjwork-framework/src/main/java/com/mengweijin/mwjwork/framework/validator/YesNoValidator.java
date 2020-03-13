package com.mengweijin.mwjwork.framework.validator;

import com.mengweijin.mwjwork.framework.validator.annotation.CharsetLength;
import com.mengweijin.mwjwork.framework.validator.annotation.YesNo;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;

/**
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-10-21 21:06
 **/
public class YesNoValidator implements ConstraintValidator<YesNo, CharSequence> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    @Override
    public void initialize(YesNo parameters) {
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return "Y".contentEquals(value) || "N".contentEquals(value);
    }



}
