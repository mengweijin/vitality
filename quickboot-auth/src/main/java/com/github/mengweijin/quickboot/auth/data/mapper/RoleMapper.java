package com.github.mengweijin.quickboot.auth.data.mapper;

import com.github.mengweijin.quickboot.auth.data.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

