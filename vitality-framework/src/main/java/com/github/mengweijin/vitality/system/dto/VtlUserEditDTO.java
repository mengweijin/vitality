package com.github.mengweijin.vitality.system.dto;

import com.github.mengweijin.vitality.system.entity.VtlUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户表 DTO
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VtlUserEditDTO extends VtlUser {

    private List<Long> deptIdList;

    private List<Long> roleIdList;

    private List<Long> postIdList;
}