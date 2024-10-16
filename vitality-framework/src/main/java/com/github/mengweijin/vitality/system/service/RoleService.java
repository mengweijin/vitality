package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vitality.system.domain.entity.Role;
import com.github.mengweijin.vitality.system.domain.entity.RoleMenu;
import com.github.mengweijin.vitality.system.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  Role Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    private RoleMenuService roleMenuService;

    /**
     * Custom paging query
     * @param page page
     * @param role {@link Role}
     * @return IPage
     */
    public IPage<Role> page(IPage<Role> page, Role role){
        LambdaQueryWrapper<Role> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(role.getCode()), Role::getCode, role.getCode())
                .eq(!Objects.isNull(role.getSeq()), Role::getSeq, role.getSeq())
                .eq(StrUtil.isNotBlank(role.getDisabled()), Role::getDisabled, role.getDisabled())
                .like(StrUtil.isNotBlank(role.getName()), Role::getName, role.getName())
                .eq(StrUtil.isNotBlank(role.getRemark()), Role::getRemark, role.getRemark())
                .eq(!Objects.isNull(role.getId()), Role::getId, role.getId())
                .eq(!Objects.isNull(role.getCreateBy()), Role::getCreateBy, role.getCreateBy())
                .eq(!Objects.isNull(role.getCreateTime()), Role::getCreateTime, role.getCreateTime())
                .eq(!Objects.isNull(role.getUpdateBy()), Role::getUpdateBy, role.getUpdateBy())
                .eq(!Objects.isNull(role.getUpdateTime()), Role::getUpdateTime, role.getUpdateTime());

        query.orderByAsc(Role::getSeq);
        return this.page(page, query);
    }

    public List<Role> getByUserId(Long userId) {
        return this.getBaseMapper().getByUserId(userId);
    }

    public Set<String> getRoleCodeByUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return this.list().stream().map(Role::getCode).collect(Collectors.toSet());
        }
        return this.getBaseMapper().getRoleCodeByUsername(username);
    }

    public boolean setMenuPermission(RolePermissionBO bo) {
        roleMenuService.removeByRoleId(bo.getRoleId());

        List<RoleMenu> collect = bo.getMenuIds().stream().map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(bo.getRoleId());
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return roleMenuService.saveBatch(collect);
        }
        return true;
    }

}
