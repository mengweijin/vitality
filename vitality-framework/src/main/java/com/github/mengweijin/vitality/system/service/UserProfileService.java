package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.UserProfileDO;
import com.github.mengweijin.vitality.system.mapper.UserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户头像存储表 服务类
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Service
public class UserProfileService extends ServiceImpl<UserProfileMapper, UserProfileDO> {

    @Autowired
    private UserProfileMapper userProfileMapper;

    public boolean updateProfileById(Long userId, String profilePicture) {
        UserProfileDO userProfile = new UserProfileDO();
        userProfile.setId(userId);
        userProfile.setProfilePicture(profilePicture);
        return this.saveOrUpdate(userProfile);
    }
}
