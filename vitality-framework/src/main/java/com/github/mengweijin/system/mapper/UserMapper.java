package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.system.dto.UserDTO;
import com.github.mengweijin.system.dto.UserDetailDTO;
import com.github.mengweijin.system.entity.UserDO;
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
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * Get VtlUser detail by id
     * @param id id
     */
    UserDetailDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<UserDTO> page(IPage<UserDTO> page, @Param("p") UserDTO dto, @Param("deptIdList") List<Long> deptIdList);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<UserDTO> pageByRole(IPage<UserDTO> page, @Param("roleId") Long roleId, @Param("p") UserDTO dto);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<UserDTO> pageByDept(IPage<UserDTO> page, @Param("deptId") Long deptId, @Param("p") UserDTO dto);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlUserDTO
     * @return IPage
     */
    IPage<UserDTO> pageByPost(IPage<UserDTO> page, @Param("postId") Long postId, @Param("p") UserDTO dto);

}
