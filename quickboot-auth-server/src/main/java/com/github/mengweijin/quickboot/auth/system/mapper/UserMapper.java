package com.github.mengweijin.quickboot.auth.system.mapper;

import com.github.mengweijin.quickboot.auth.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

