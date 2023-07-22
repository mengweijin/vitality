package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.system.entity.UserProfileDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户头像存储表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Mapper
public interface UserProfileMapper extends BaseMapper<UserProfileDO> {

}
