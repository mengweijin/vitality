package com.github.mengweijin.quickboot.auth.system.service;

import com.github.mengweijin.quickboot.auth.system.entity.UserRole;
import com.github.mengweijin.quickboot.auth.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和用户关联表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> implements IService<UserRole> {

    /**
     * <p>
     * UserRoleMapper
     * </p>
     */
    @Autowired
    private UserRoleMapper userRoleMapper;
}

