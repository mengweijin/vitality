package com.github.mengweijin.system.dto;

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
public class UserDetailDTO extends UserDTO {

    private String profilePicture;

    private List<DeptDTO> deptList = new ArrayList<>();

    private List<RoleDTO> roleList = new ArrayList<>();

    private List<PostDTO> postList = new ArrayList<>();

}