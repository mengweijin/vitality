package com.github.mengweijin.quickboot.auth.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.quickboot.auth.system.entity.User;
import com.github.mengweijin.quickboot.auth.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

    /**
     * <p>
     * UserMapper
     * </p>
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    public User getByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        return userMapper.selectOne(lambdaQueryWrapper);
    }
}

