package com.github.mengweijin.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.mybatisplus.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mengweijin
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
