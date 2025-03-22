package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.bo.ChangePasswordBO;
import com.github.mengweijin.vita.system.domain.entity.Config;
import com.github.mengweijin.vita.system.domain.entity.User;
import com.github.mengweijin.vita.system.domain.entity.UserAvatar;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.enums.EMessageTemplate;
import com.github.mengweijin.vita.system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
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

    private MessageService messageService;

    private ConfigService configService;

    @Override
    public boolean save(User user) {
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        String hashedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPwd);
        user.setPasswordChangeTime(LocalDateTime.now());
        return super.save(user);
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
            deptIds = deptService.selectChildrenIdsWithCurrentIdById(user.getDeptId());
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

    public Set<Long> getUserIdsInDeptId(Long deptId) {
        List<Long> deptIds = deptService.selectChildrenIdsWithCurrentIdById(deptId);
        List<User> list = this.lambdaQuery().select(User::getId).in(User::getDeptId, deptIds).list();
        return list.stream().map(User::getId).collect(Collectors.toSet());
    }

    public String getUsernameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUsernameById).collect(Collectors.joining());
    }

    public String getUserNicknameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getNicknameById).collect(Collectors.joining());
    }

    @Cacheable(value = CacheNames.USER_ID_TO_USERNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getUsernameById(Long id) {
        return this.lambdaQuery()
                .select(User::getUsername)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getUsername)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_NICKNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNicknameById(Long id) {
        return this.lambdaQuery()
                .select(User::getNickname)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getNickname)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_AVATAR, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getAvatarById(Long id) {
        return userAvatarService.lambdaQuery().eq(UserAvatar::getUserId, id).oneOpt()
                .map(UserAvatar::getAvatar).orElse(null);
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
        return this.lambdaUpdate()
                .set(User::getPassword, hashedPwd)
                .set(User::getPasswordLevel, passwordLevel)
                .set(User::getPasswordChangeTime, LocalDateTime.now())
                .eq(User::getUsername, username)
                .update();
    }

    public boolean setDisabled(Long id, String disabled) {
        return this.lambdaUpdate().set(User::getDisabled, disabled).eq(User::getId, id).update();
    }

    public void checkAndSendPasswordLongTimeNoChangeMessageAsync(String username) {
        CompletableFuture.runAsync(() -> {
                    Config config = configService.getByCode(ConfigConst.USER_PASSWORD_CHANGE_INTERVAL);
                    if (config == null) {
                        return;
                    }
                    long daysInterval = NumberUtil.parseLong(config.getVal());
                    if (daysInterval <= 0) {
                        return;
                    }

                    User user = this.getByUsername(username);
                    Duration duration = TimeUtil.between(user.getPasswordChangeTime(), LocalDateTime.now());
                    if (duration.toDays() < daysInterval) {
                        return;
                    }

                    messageService.sendMessageToUser(user.getId(), EMessageCategory.SECURITY, EMessageTemplate.USER_PASSWORD_LONG_TIME_NO_CHANGE, duration.toDays());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }
}
