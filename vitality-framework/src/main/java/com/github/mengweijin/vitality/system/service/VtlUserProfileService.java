package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlUserProfile;
import com.github.mengweijin.vitality.system.mapper.VtlUserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户头像存储表 服务类
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Service
public class VtlUserProfileService extends ServiceImpl<VtlUserProfileMapper, VtlUserProfile> {

    @Autowired
    private VtlUserProfileMapper vtlUserProfileMapper;

    public boolean updateProfileById(Long userId, String profilePicture) {
        VtlUserProfile userProfile = new VtlUserProfile();
        userProfile.setId(userId);
        userProfile.setProfilePicture(profilePicture);
        return this.saveOrUpdate(userProfile);
    }
}
