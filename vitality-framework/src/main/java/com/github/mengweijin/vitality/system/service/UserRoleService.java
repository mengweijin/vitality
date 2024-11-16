package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.system.domain.bo.UserRolesBO;
import com.github.mengweijin.vitality.system.domain.entity.UserRole;
import com.github.mengweijin.vitality.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * User Role Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserRoleService extends CrudRepository<UserRoleMapper, UserRole> {

    public Set<Long> getRoleIdsByUserId(Long userId) {
        List<UserRole> list = this.lambdaQuery().select(UserRole::getRoleId).eq(UserRole::getUserId, userId).list();
        return list.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    public Set<Long> getUserIdsByRoleId(Long roleId) {
        List<UserRole> list = this.lambdaQuery().select(UserRole::getUserId).eq(UserRole::getRoleId, roleId).list();
        return list.stream().map(UserRole::getUserId).collect(Collectors.toSet());
    }

    public boolean setUserRoles(UserRolesBO bo) {
        Long userId = bo.getUserId();
        List<Long> roleIds = bo.getRoleIds();
        this.lambdaUpdate().eq(UserRole::getUserId, userId).remove();

        List<UserRole> list = roleIds.stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).toList();

        return this.saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
    }
}
