package com.github.mengweijin.vitality.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户表 DTO
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VtlUserDetailDTO extends VtlUserDTO {

    private String profilePicture;

    private List<VtlDeptDTO> deptList = new ArrayList<>();

    private List<VtlRoleDTO> roleList = new ArrayList<>();

    private List<VtlPostDTO> postList = new ArrayList<>();

}