package com.github.mengweijin.mybatisplus.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.mybatisplus.demo.entity.User;
import com.github.mengweijin.mybatisplus.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mengweijin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
}
