package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.cache.CacheConst;
import com.github.mengweijin.vitality.framework.cache.CacheName;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                .eq(!Objects.isNull(user.getId()), User::getId, user.getId())
                .eq(!Objects.isNull(user.getCreateBy()), User::getCreateBy, user.getCreateBy())
                .eq(!Objects.isNull(user.getCreateTime()), User::getCreateTime, user.getCreateTime())
                .eq(!Objects.isNull(user.getUpdateBy()), User::getUpdateBy, user.getUpdateBy())
                .eq(!Objects.isNull(user.getUpdateTime()), User::getUpdateTime, user.getUpdateTime());
        return this.page(page, query);
    }

    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    public String getUsernameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUsernameById).collect(Collectors.joining());
    }

    public String getUserNicknameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUserNicknameById).collect(Collectors.joining());
    }

    @Cacheable(value = CacheName.USER_ID_TO_USERNAME, key = "#id", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getUsernameById(Long id) {
        return this.lambdaQuery()
                .select(User::getUsername)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getUsername)
                .orElse(null);
    }

    @Cacheable(value = CacheName.USER_ID_TO_NICKNAME, key = "#id", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getUserNicknameById(Long id) {
        return this.lambdaQuery()
                .select(User::getNickname)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getNickname)
                .orElse(null);
    }

    @CacheEvict(value = CacheName.USER_ID_TO_USERNAME, key = "#id")
    public void removeCacheOfUsername(Long id) {}

    @CacheEvict(value = CacheName.USER_ID_TO_NICKNAME, key = "#id")
    public void removeCacheOfNickname(Long id) {}
}
