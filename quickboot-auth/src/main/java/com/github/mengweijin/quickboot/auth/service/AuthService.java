package com.github.mengweijin.quickboot.auth.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quickboot.auth.entity.Auth;
import com.github.mengweijin.quickboot.auth.mapper.AuthMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class AuthService extends ServiceImpl<AuthMapper, Auth> implements IService<Auth> {

    /**
     * <p>
     * AuthMapper
     * </p>
     */
    @Autowired
    private AuthMapper authMapper;
}

