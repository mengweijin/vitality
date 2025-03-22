package com.github.mengweijin.vitality.system.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mengweijin
 */
@Data
public class UserRolesBO implements Serializable {

    @NotNull
    private Long userId;

    @NotEmpty
    private List<Long> roleIds;

}
