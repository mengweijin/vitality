package com.github.mengweijin.system.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.constant.UserConst;
import com.github.mengweijin.system.domain.entity.User;
import com.github.mengweijin.system.domain.vo.UserSessionVO;
import com.github.mengweijin.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  User Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Override
    public boolean save(User user) {
        String hashedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPwd);
        return super.save(user);
    }

    /**
     * Custom paging query
     * @param page page
     * @param user {@link User}
     * @return IPage
     */
    public IPage<User> page(IPage<User> page, User user){
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .eq(StrUtil.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .eq(StrUtil.isNotBlank(user.getPassword()), User::getPassword, user.getPassword())
                .eq(StrUtil.isNotBlank(user.getIdCard()), User::getIdCard, user.getIdCard())
                .eq(StrUtil.isNotBlank(user.getGender()), User::getGender, user.getGender())
                .eq(StrUtil.isNotBlank(user.getEmail()), User::getEmail, user.getEmail())
                .eq(StrUtil.isNotBlank(user.getMobile()), User::getMobile, user.getMobile())
                .eq(StrUtil.isNotBlank(user.getSecretKey()), User::getSecretKey, user.getSecretKey())
                .eq(StrUtil.isNotBlank(user.getDisabled()), User::getDisabled, user.getDisabled())
                .eq(StrUtil.isNotBlank(user.getDeleted()), User::getDeleted, user.getDeleted())
                .eq(!Objects.isNull(user.getId()), User::getId, user.getId())
                .eq(!Objects.isNull(user.getCreateBy()), User::getCreateBy, user.getCreateBy())
                .eq(!Objects.isNull(user.getCreateTime()), User::getCreateTime, user.getCreateTime())
                .eq(!Objects.isNull(user.getUpdateBy()), User::getUpdateBy, user.getUpdateBy())
                .eq(!Objects.isNull(user.getUpdateTime()), User::getUpdateTime, user.getUpdateTime());
        return this.page(page, query);
    }

    public void setSessionUser(UserSessionVO userSessionVO) {
        StpUtil.getSession().set(UserConst.SESSION_USER, userSessionVO);
    }

    public UserSessionVO getSessionUser() {
        return (UserSessionVO) StpUtil.getSession().get(UserConst.SESSION_USER);
    }

    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
