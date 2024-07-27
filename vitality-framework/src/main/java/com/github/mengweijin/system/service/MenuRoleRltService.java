package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.entity.MenuRoleRltDO;
import com.github.mengweijin.system.mapper.MenuRoleRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-角色关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class MenuRoleRltService extends ServiceImpl<MenuRoleRltMapper, MenuRoleRltDO> {

    @Autowired
    private MenuRoleRltMapper menuRoleRltMapper;

}
