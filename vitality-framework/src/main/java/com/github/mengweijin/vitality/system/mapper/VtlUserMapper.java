package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Mapper
public interface VtlUserMapper extends BaseMapper<VtlUser> {

    /**
     * Get VtlUser detail by id
     * @param id id
     */
    VtlUserDetailDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<VtlUserDTO> page(IPage<VtlUserDTO> page, @Param("p") VtlUserDTO dto, @Param("deptIdList") List<Long> deptIdList);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<VtlUserDTO> pageByRole(IPage<VtlUserDTO> page, @Param("roleId") Long roleId, @Param("p") VtlUserDTO dto);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<VtlUserDTO> pageByDept(IPage<VtlUserDTO> page, @Param("deptId") Long deptId, @Param("p") VtlUserDTO dto);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<VtlUserDTO> pageByPost(IPage<VtlUserDTO> page, @Param("postId") Long postId, @Param("p") VtlUserDTO dto);

}
