package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色管理表 DTO
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VtlRoleDTO extends VtlRole {

    private Long userCount;

}
