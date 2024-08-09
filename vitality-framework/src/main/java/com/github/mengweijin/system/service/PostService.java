package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.Post;
import com.github.mengweijin.system.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  Post Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class PostService extends ServiceImpl<PostMapper, Post> {

    /**
     * Custom paging query
     * @param page page
     * @param post {@link Post}
     * @return IPage
     */
    public IPage<Post> page(IPage<Post> page, Post post){
        LambdaQueryWrapper<Post> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(post.getName()), Post::getName, post.getName())
                .eq(StrUtil.isNotBlank(post.getCode()), Post::getCode, post.getCode())
                .eq(!Objects.isNull(post.getSeq()), Post::getSeq, post.getSeq())
                .eq(StrUtil.isNotBlank(post.getDisabled()), Post::getDisabled, post.getDisabled())
                .eq(StrUtil.isNotBlank(post.getRemark()), Post::getRemark, post.getRemark())
                .eq(!Objects.isNull(post.getId()), Post::getId, post.getId())
                .eq(!Objects.isNull(post.getCreateBy()), Post::getCreateBy, post.getCreateBy())
                .eq(!Objects.isNull(post.getCreateTime()), Post::getCreateTime, post.getCreateTime())
                .eq(!Objects.isNull(post.getUpdateBy()), Post::getUpdateBy, post.getUpdateBy())
                .eq(!Objects.isNull(post.getUpdateTime()), Post::getUpdateTime, post.getUpdateTime());
        return this.page(page, query);
    }
}
