package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.system.domain.entity.User;
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

