package com.github.mengweijin.vitality.system.domain.bo;

import com.github.mengweijin.vitality.framework.constant.ConstRegex;
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
    @Pattern(regexp = ConstRegex.PASSWORD, message = "{user.password.pattern}")
    private String password;

    @NotBlank
    @Pattern(regexp = ConstRegex.PASSWORD, message = "{user.password.pattern}")
    private String newPassword;

}
