package com.github.mengweijin.system.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
public class LoginBody implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean rememberMe;

    private String captcha;
}
