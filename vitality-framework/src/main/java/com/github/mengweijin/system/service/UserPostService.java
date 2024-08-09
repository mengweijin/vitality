package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.UserPost;
import com.github.mengweijin.system.mapper.UserPostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  UserPost Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserPostService extends ServiceImpl<UserPostMapper, UserPost> {

    /**
     * Custom paging query
     * @param page page
     * @param userPost {@link UserPost}
     * @return IPage
     */
    public IPage<UserPost> page(IPage<UserPost> page, UserPost userPost){
        LambdaQueryWrapper<UserPost> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(userPost.getUserId()), UserPost::getUserId, userPost.getUserId())
                .eq(!Objects.isNull(userPost.getPostId()), UserPost::getPostId, userPost.getPostId())
                .eq(!Objects.isNull(userPost.getId()), UserPost::getId, userPost.getId())
                .eq(!Objects.isNull(userPost.getCreateBy()), UserPost::getCreateBy, userPost.getCreateBy())
                .eq(!Objects.isNull(userPost.getCreateTime()), UserPost::getCreateTime, userPost.getCreateTime())
                .eq(!Objects.isNull(userPost.getUpdateBy()), UserPost::getUpdateBy, userPost.getUpdateBy())
                .eq(!Objects.isNull(userPost.getUpdateTime()), UserPost::getUpdateTime, userPost.getUpdateTime());
        return this.page(page, query);
    }
}
