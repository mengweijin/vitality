package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.RoleMenu;
import com.github.mengweijin.vita.system.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Role Menu Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class RoleMenuService extends CrudRepository<RoleMenuMapper, RoleMenu> {

    public List<Long> getMenuIdsByRoleId(Long roleId) {
        List<RoleMenu> roleMenuList = this.lambdaQuery().select(RoleMenu::getMenuId).eq(RoleMenu::getRoleId, roleId).list();
        return roleMenuList.stream().map(RoleMenu::getMenuId).toList();
    }

    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        this.getBaseMapper().delete(wrapper);
    }
}
