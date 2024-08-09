package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.system.domain.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Post Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}

