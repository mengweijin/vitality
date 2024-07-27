package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.entity.MenuUserRltDO;
import com.github.mengweijin.system.mapper.MenuUserRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-用户关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class MenuUserRltService extends ServiceImpl<MenuUserRltMapper, MenuUserRltDO> {

    @Autowired
    private MenuUserRltMapper menuUserRltMapper;

}
