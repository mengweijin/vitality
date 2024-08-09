package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.system.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  User Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

