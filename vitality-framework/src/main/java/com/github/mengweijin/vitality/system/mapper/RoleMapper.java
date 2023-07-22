package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.RoleDTO;
import com.github.mengweijin.vitality.system.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    /**
     * Get VtlRole detail by id
     * @param id id
     */
    RoleDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlRoleDTO
     * @return IPage
     */
    IPage<RoleDTO> page(IPage<RoleDTO> page, @Param("p") RoleDTO dto);

    List<RoleDTO> getByUserId(Long userId);
}
