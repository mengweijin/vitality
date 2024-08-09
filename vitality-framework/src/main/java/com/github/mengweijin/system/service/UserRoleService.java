package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.UserRole;
import com.github.mengweijin.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  UserRole Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * Custom paging query
     * @param page page
     * @param userRole {@link UserRole}
     * @return IPage
     */
    public IPage<UserRole> page(IPage<UserRole> page, UserRole userRole){
        LambdaQueryWrapper<UserRole> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(userRole.getUserId()), UserRole::getUserId, userRole.getUserId())
                .eq(!Objects.isNull(userRole.getRoleId()), UserRole::getRoleId, userRole.getRoleId())
                .eq(!Objects.isNull(userRole.getId()), UserRole::getId, userRole.getId())
                .eq(!Objects.isNull(userRole.getCreateBy()), UserRole::getCreateBy, userRole.getCreateBy())
                .eq(!Objects.isNull(userRole.getCreateTime()), UserRole::getCreateTime, userRole.getCreateTime())
                .eq(!Objects.isNull(userRole.getUpdateBy()), UserRole::getUpdateBy, userRole.getUpdateBy())
                .eq(!Objects.isNull(userRole.getUpdateTime()), UserRole::getUpdateTime, userRole.getUpdateTime());
        return this.page(page, query);
    }
}
