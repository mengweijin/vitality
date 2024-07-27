package com.github.mengweijin.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author mengweijin
 * @date 2023/6/22
 */
@Data
public class UserChangePasswordDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;

}
