package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.system.entity.MenuDeptRltDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单-部门关联表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Mapper
public interface MenuDeptRltMapper extends BaseMapper<MenuDeptRltDO> {

}
