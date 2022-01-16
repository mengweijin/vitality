package com.github.mengweijin.quickboot.sample.auth.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.sample.auth.client.entity.User;
import com.github.mengweijin.quickboot.sample.auth.client.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-16
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
}

