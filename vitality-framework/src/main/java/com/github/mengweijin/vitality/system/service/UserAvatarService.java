package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.domain.entity.UserAvatar;
import com.github.mengweijin.vitality.system.mapper.UserAvatarMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * User Avatar Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserAvatarService extends ServiceImpl<UserAvatarMapper, UserAvatar> {

    public boolean setAvatar(UserAvatar userAvatar) {
        SpringUtil.getBean(UserService.class).removeCacheOfAvatar(userAvatar.getUserId());

        Optional<UserAvatar> optional = this.lambdaQuery().eq(UserAvatar::getUserId, userAvatar.getUserId()).oneOpt();
        optional.ifPresent(avatar -> userAvatar.setId(avatar.getId()));
        return this.saveOrUpdate(userAvatar);
    }
}
