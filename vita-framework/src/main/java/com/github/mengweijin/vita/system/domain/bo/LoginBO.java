package com.github.mengweijin.vita.system.domain.bo;

import com.github.mengweijin.vita.framework.constant.Regex;
import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.system.validator.rule.CaptchaCheckRule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
public class LoginBO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = Regex.PASSWORD, message = "{user.password.pattern}")
    private String password;

    @BusinessCheck(checkRule = CaptchaCheckRule.class)
    private String captcha;

    private boolean rememberMe;

}
