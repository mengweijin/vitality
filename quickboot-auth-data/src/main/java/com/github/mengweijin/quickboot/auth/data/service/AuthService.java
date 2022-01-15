package com.github.mengweijin.quickboot.auth.data.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.quickboot.auth.data.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quickboot.auth.data.entity.Auth;
import com.github.mengweijin.quickboot.auth.data.mapper.AuthMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private UserRoleService userRoleService;

    public List<Auth> selectAuthByUserId(Serializable userId) {
        List<Auth> authList = new ArrayList<>();

        LambdaQueryWrapper<UserRole> roleUserWrapper = new LambdaQueryWrapper<>();
        roleUserWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> roleUserList = userRoleService.list(roleUserWrapper);

        // 当前用户没有分配角色
        if(CollectionUtil.isEmpty(roleUserList)) {
            // ignore. No need to add Authority.
        } else {
            // 筛选 roleId=1 的记录（roleId=1 表示角色为管理员，管理员拥有所有权限。）。
            boolean isAdmin = roleUserList.stream().anyMatch(roleUser -> roleUser.getRoleId() == 1L);
            if(isAdmin) {
                authList = this.list();
            } else {
                Optional<List<Auth>> optional = authMapper.selectAuthByUserId(userId);
                if(optional.isPresent()) {
                    authList = optional.get();
                }
            }
        }

        return authList;
    }
}

