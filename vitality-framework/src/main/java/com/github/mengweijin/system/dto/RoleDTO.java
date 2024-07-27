package com.github.mengweijin.system.dto;

import com.github.mengweijin.system.entity.RoleDO;
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
public class RoleDTO extends RoleDO {

    private Long userCount;

}
