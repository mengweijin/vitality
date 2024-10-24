package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.framework.cache.CacheConst;
import com.github.mengweijin.vitality.framework.cache.CacheName;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.util.AopUtils;
import com.github.mengweijin.vitality.system.domain.bo.ChangePasswordBO;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.domain.entity.UserAvatar;
import com.github.mengweijin.vitality.system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * User Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService extends CrudRepository<UserMapper, User> {

    private UserAvatarService userAvatarService;

    private DeptService deptService;

    @Override
    public boolean save(User user) {
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        String hashedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPwd);
        return super.save(user);
    }

    @Override
    public boolean updateById(User entity) {
        AopUtils.getAopProxy(this).removeCacheOfUsername(entity.getId());
        AopUtils.getAopProxy(this).removeCacheOfNickname(entity.getId());
        AopUtils.getAopProxy(this).removeCacheOfAvatar(entity.getId());
        return super.updateById(entity);
    }

    /**
     * Custom paging query
     *
     * @param page page
     * @param user {@link User}
     * @return IPage
     */
    public IPage<User> page(IPage<User> page, User user) {
        List<Long> deptIds = new ArrayList<>();
        if (!Objects.isNull(user.getDeptId())) {
            deptIds = deptService.getDeptChildrenIdsWithCurrentById(user.getDeptId());
        }
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(user.getPasswordLevel()), User::getPassword, user.getPasswordLevel())
                .eq(StrUtil.isNotBlank(user.getIdCard()), User::getIdCard, user.getIdCard())
                .eq(StrUtil.isNotBlank(user.getGender()), User::getGender, user.getGender())
                .eq(StrUtil.isNotBlank(user.getDisabled()), User::getDisabled, user.getDisabled())
                .eq(StrUtil.isNotBlank(user.getRemark()), User::getRemark, user.getRemark())
                .eq(!Objects.isNull(user.getId()), User::getId, user.getId())
                .eq(!Objects.isNull(user.getCreateBy()), User::getCreateBy, user.getCreateBy())
                .eq(!Objects.isNull(user.getCreateTime()), User::getCreateTime, user.getCreateTime())
                .eq(!Objects.isNull(user.getUpdateBy()), User::getUpdateBy, user.getUpdateBy())
                .eq(!Objects.isNull(user.getUpdateTime()), User::getUpdateTime, user.getUpdateTime())
                .in(!Objects.isNull(user.getDeptId()), User::getDeptId, deptIds)
                .like(StrUtil.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .like(StrUtil.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .like(StrUtil.isNotBlank(user.getMobile()), User::getMobile, user.getMobile())
                .like(StrUtil.isNotBlank(user.getEmail()), User::getEmail, user.getEmail());
        return this.page(page, query);
    }

    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    public String getUsernameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUsernameById).collect(Collectors.joining());
    }

    public String getUserNicknameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getNicknameById).collect(Collectors.joining());
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
    public String getNicknameById(Long id) {
        return this.lambdaQuery()
                .select(User::getNickname)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getNickname)
                .orElse(null);
    }

    @Cacheable(value = CacheName.USER_ID_TO_AVATAR, key = "#id", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getAvatarById(Long id) {
        return userAvatarService.lambdaQuery().eq(UserAvatar::getUserId, id).oneOpt()
                .map(UserAvatar::getAvatar).orElse(null);
    }

    @CacheEvict(value = CacheName.USER_ID_TO_USERNAME, key = "#id")
    public void removeCacheOfUsername(Long id) {
    }

    @CacheEvict(value = CacheName.USER_ID_TO_NICKNAME, key = "#id")
    public void removeCacheOfNickname(Long id) {
    }

    @CacheEvict(value = CacheName.USER_ID_TO_AVATAR, key = "#id")
    public void removeCacheOfAvatar(Long id) {
    }

    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    public boolean changePassword(ChangePasswordBO bo) {
        User user = this.getByUsername(bo.getUsername());
        boolean checked = this.checkPassword(bo.getPassword(), user.getPassword());
        if (!checked) {
            throw new ClientException("User or password check failed!");
        }
        return this.updatePassword(bo.getUsername(), bo.getNewPassword());
    }

    public boolean updatePassword(String username, String password) {
        String passwordLevel = PasswdStrength.getLevel(password).name();
        String hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt());
        return this.lambdaUpdate().set(User::getPassword, hashedPwd).set(User::getPasswordLevel, passwordLevel).eq(User::getUsername, username).update();
    }

}
