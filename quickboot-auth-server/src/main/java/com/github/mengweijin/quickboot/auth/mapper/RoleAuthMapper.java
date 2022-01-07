package com.github.mengweijin.quickboot.auth.mapper;

import com.github.mengweijin.quickboot.auth.entity.RoleAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和权限关联表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Mapper
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {

}

