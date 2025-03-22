package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.UserAvatar;
import com.github.mengweijin.vita.system.mapper.UserAvatarMapper;
import lombok.extern.slf4j.Slf4j;
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
public class UserAvatarService extends CrudRepository<UserAvatarMapper, UserAvatar> {

    public boolean setAvatar(UserAvatar userAvatar) {
        Optional<UserAvatar> optional = this.lambdaQuery().eq(UserAvatar::getUserId, userAvatar.getUserId()).oneOpt();
        optional.ifPresent(avatar -> userAvatar.setId(avatar.getId()));
        return this.saveOrUpdate(userAvatar);
    }
}
