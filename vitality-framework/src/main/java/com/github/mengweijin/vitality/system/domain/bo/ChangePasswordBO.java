package com.github.mengweijin.vitality.system.domain.bo;

import com.github.mengweijin.vitality.framework.constant.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
public class ChangePasswordBO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = Regex.PASSWORD, message = "{user.password.pattern}")
    private String password;

    @NotBlank
    @Pattern(regexp = Regex.PASSWORD, message = "{user.password.pattern}")
    private String newPassword;

}
