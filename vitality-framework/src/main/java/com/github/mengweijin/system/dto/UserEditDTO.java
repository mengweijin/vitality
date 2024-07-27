package com.github.mengweijin.system.dto;

import com.github.mengweijin.system.entity.UserDO;
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
public class UserEditDTO extends UserDO {

    private List<Long> deptIdList;

    private List<Long> roleIdList;

    private List<Long> postIdList;
}