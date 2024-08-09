package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.UserProfile;
import com.github.mengweijin.system.mapper.UserProfileMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  UserProfile Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserProfileService extends ServiceImpl<UserProfileMapper, UserProfile> {

    /**
     * Custom paging query
     * @param page page
     * @param userProfile {@link UserProfile}
     * @return IPage
     */
    public IPage<UserProfile> page(IPage<UserProfile> page, UserProfile userProfile){
        LambdaQueryWrapper<UserProfile> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(userProfile.getUserId()), UserProfile::getUserId, userProfile.getUserId())
                .eq(StrUtil.isNotBlank(userProfile.getProfile()), UserProfile::getProfile, userProfile.getProfile())
                .eq(!Objects.isNull(userProfile.getId()), UserProfile::getId, userProfile.getId())
                .eq(!Objects.isNull(userProfile.getCreateBy()), UserProfile::getCreateBy, userProfile.getCreateBy())
                .eq(!Objects.isNull(userProfile.getCreateTime()), UserProfile::getCreateTime, userProfile.getCreateTime())
                .eq(!Objects.isNull(userProfile.getUpdateBy()), UserProfile::getUpdateBy, userProfile.getUpdateBy())
                .eq(!Objects.isNull(userProfile.getUpdateTime()), UserProfile::getUpdateTime, userProfile.getUpdateTime());
        return this.page(page, query);
    }
}
