package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.system.domain.entity.UserPreference;
import com.github.mengweijin.vitality.system.mapper.UserPreferenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * UserPreference Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserPreferenceService extends ServiceImpl<UserPreferenceMapper, UserPreference> {

    /**
     * Custom paging query
     *
     * @param page           page
     * @param userPreference {@link UserPreference}
     * @return IPage
     */
    public IPage<UserPreference> page(IPage<UserPreference> page, UserPreference userPreference) {
        LambdaQueryWrapper<UserPreference> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(userPreference.getUserId()), UserPreference::getUserId, userPreference.getUserId())
                .eq(StrUtil.isNotBlank(userPreference.getUserMessage()), UserPreference::getUserMessage, userPreference.getUserMessage())
                .eq(StrUtil.isNotBlank(userPreference.getSystemMessage()), UserPreference::getSystemMessage, userPreference.getSystemMessage())
                .eq(StrUtil.isNotBlank(userPreference.getTodoTask()), UserPreference::getTodoTask, userPreference.getTodoTask())
                .eq(!Objects.isNull(userPreference.getId()), UserPreference::getId, userPreference.getId())
                .eq(!Objects.isNull(userPreference.getCreateBy()), UserPreference::getCreateBy, userPreference.getCreateBy())
                .eq(!Objects.isNull(userPreference.getCreateTime()), UserPreference::getCreateTime, userPreference.getCreateTime())
                .eq(!Objects.isNull(userPreference.getUpdateBy()), UserPreference::getUpdateBy, userPreference.getUpdateBy())
                .eq(!Objects.isNull(userPreference.getUpdateTime()), UserPreference::getUpdateTime, userPreference.getUpdateTime());
        return this.page(page, query);
    }

    public UserPreference getUserPreferenceByLoginUser() {
        Long userId = LoginHelper.getLoginUser().getUserId();
        return this.lambdaQuery().eq(UserPreference::getUserId, userId).one();
    }

    public boolean saveOrUpdateByLoginUser(UserPreference userPreference) {
        UserPreference queried = this.getUserPreferenceByLoginUser();
        if (queried != null) {
            userPreference.setId(queried.getId());
        }

        Long userId = LoginHelper.getLoginUser().getUserId();
        userPreference.setUserId(userId);
        return this.saveOrUpdate(userPreference);
    }
}
