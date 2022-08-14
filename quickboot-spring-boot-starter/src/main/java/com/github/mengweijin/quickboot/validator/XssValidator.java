package com.github.mengweijin.quickboot.validator;

import com.github.mengweijin.quickboot.validator.annotation.Xss;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义xss校验注解实现
 *
 * @author ruoyi
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !containsHtml(value);
    }

    public boolean containsHtml(String value) {
        String htmlPattern = "<(\\S*?)[^>]*>.*?|<.*? />";
        Pattern pattern = Pattern.compile(htmlPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}