package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.framework.exception.ClientException;
import com.github.mengweijin.system.dto.RoleDTO;
import com.github.mengweijin.system.entity.MenuRoleRltDO;
import com.github.mengweijin.system.entity.RoleDO;
import com.github.mengweijin.system.entity.UserRoleRltDO;
import com.github.mengweijin.system.mapper.RoleMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 角色管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleDO> {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleRltService userRoleRltService;
    @Autowired
    private MenuRoleRltService menuRoleRltService;

    @Override
    public boolean removeById(Serializable id) {
        Long count = userRoleRltService.lambdaQuery().eq(UserRoleRltDO::getRoleId, id).count();
        if(count > 0) {
            throw new ClientException("Users already exist in current role and cannot be deleted!");
        }
        return super.removeById(id);
    }

    public RoleDTO detailById(Long id) {
        return roleMapper.detailById(id);
    }

    public IPage<RoleDTO> page(IPage<RoleDTO> page, RoleDTO dto){
        return roleMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(RoleDO::getDisabled, disabled).eq(RoleDO::getId, id).update();
    }

    public List<RoleDTO> getByUserId(Long userId) {
        return roleMapper.getByUserId(userId);
    }

    public void addUsers(Long roleId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        List<Long> alreadyExistedUserIdList = userRoleRltService.lambdaQuery()
                .select(UserRoleRltDO::getUserId)
                .eq(UserRoleRltDO::getRoleId, roleId)
                .in(UserRoleRltDO::getUserId, userIdList)
                .list()
                .stream().map(UserRoleRltDO::getUserId).toList();

        List<UserRoleRltDO> userRoleRltList = userIdList.stream()
                .filter(userId -> alreadyExistedUserIdList.stream().noneMatch(existed -> existed.equals(userId)))
                .map(userId -> {
                    UserRoleRltDO rlt = new UserRoleRltDO();
                    rlt.setUserId(userId);
                    rlt.setRoleId(roleId);
                    return rlt;
                })
                .toList();

        if (CollUtil.isNotEmpty(userRoleRltList)) {
            userRoleRltService.saveBatch(userRoleRltList);
        }
    }

    public void removeUsers(Long roleId, List<Long> userIdList) {
        if(CollUtil.isEmpty(userIdList)) {
            return;
        }
        userRoleRltService.lambdaUpdate()
                .eq(UserRoleRltDO::getRoleId, roleId)
                .in(UserRoleRltDO::getUserId, userIdList)
                .remove();
    }

    @Transactional
    public void setMenu(Long id, List<Long> menuIdList) {
        menuRoleRltService.lambdaUpdate().eq(MenuRoleRltDO::getRoleId, id).remove();
        if(CollUtil.isEmpty(menuIdList)) {
            return;
        }
        List<MenuRoleRltDO> list = menuIdList.stream().map(menuId -> new MenuRoleRltDO().setRoleId(id).setMenuId(menuId)).toList();
        menuRoleRltService.saveBatch(list);
    }
}
