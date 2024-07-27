package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.system.entity.MenuRoleRltDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单-角色关联表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Mapper
public interface MenuRoleRltMapper extends BaseMapper<MenuRoleRltDO> {

}
