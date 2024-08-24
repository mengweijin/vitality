package com.github.mengweijin.vitality.system.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
public class UpdatePasswordBO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;

}
