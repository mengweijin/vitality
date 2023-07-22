package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.MenuDeptRltDO;
import com.github.mengweijin.vitality.system.mapper.MenuDeptRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-部门关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class MenuDeptRltService extends ServiceImpl<MenuDeptRltMapper, MenuDeptRltDO> {

    @Autowired
    private MenuDeptRltMapper menuDeptRltMapper;

}
