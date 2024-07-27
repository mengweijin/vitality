package com.github.mengweijin.system.dto;

import com.github.mengweijin.system.entity.DeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门管理表 DTO
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends DeptDO {

    private Long userCount;

}
