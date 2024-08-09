package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.RoleMenu;
import com.github.mengweijin.system.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  RoleMenu Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {

    /**
     * Custom paging query
     * @param page page
     * @param roleMenu {@link RoleMenu}
     * @return IPage
     */
    public IPage<RoleMenu> page(IPage<RoleMenu> page, RoleMenu roleMenu){
        LambdaQueryWrapper<RoleMenu> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(roleMenu.getRoleId()), RoleMenu::getRoleId, roleMenu.getRoleId())
                .eq(!Objects.isNull(roleMenu.getMenuId()), RoleMenu::getMenuId, roleMenu.getMenuId())
                .eq(!Objects.isNull(roleMenu.getId()), RoleMenu::getId, roleMenu.getId())
                .eq(!Objects.isNull(roleMenu.getCreateBy()), RoleMenu::getCreateBy, roleMenu.getCreateBy())
                .eq(!Objects.isNull(roleMenu.getCreateTime()), RoleMenu::getCreateTime, roleMenu.getCreateTime())
                .eq(!Objects.isNull(roleMenu.getUpdateBy()), RoleMenu::getUpdateBy, roleMenu.getUpdateBy())
                .eq(!Objects.isNull(roleMenu.getUpdateTime()), RoleMenu::getUpdateTime, roleMenu.getUpdateTime());
        return this.page(page, query);
    }
}
