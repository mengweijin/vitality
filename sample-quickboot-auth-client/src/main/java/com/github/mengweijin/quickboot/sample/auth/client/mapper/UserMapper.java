package com.github.mengweijin.quickboot.sample.auth.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.quickboot.sample.auth.client.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

