package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.entity.VtlRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Mapper
public interface VtlRoleMapper extends BaseMapper<VtlRole> {

    /**
     * Get VtlRole detail by id
     * @param id id
     */
    VtlRoleDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlRoleDTO
     * @return IPage
     */
    IPage<VtlRoleDTO> page(IPage<VtlRoleDTO> page, @Param("p") VtlRoleDTO dto);

}
