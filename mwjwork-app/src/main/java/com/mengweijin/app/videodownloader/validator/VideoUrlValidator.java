package com.mengweijin.app.videodownloader.validator;

import com.mengweijin.app.videodownloader.runner.enums.EWebSite;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;

/**
 * @author mengweijin
 */
public class VideoUrlValidator implements ConstraintValidator<VideoUrlValidate, CharSequence> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    
    @Override
    public void initialize(VideoUrlValidate parameters) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return isSupportedUrl(value);
    }

    private boolean isSupportedUrl(CharSequence value) {
        EWebSite[] values = EWebSite.values();
        for (EWebSite site: values) {
            if(value.toString().contains(site.getWebSite())) {
                return true;
            }
        }
        return false;
    }

    
}
