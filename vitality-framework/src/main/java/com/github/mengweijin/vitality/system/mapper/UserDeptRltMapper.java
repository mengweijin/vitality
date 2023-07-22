package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.system.entity.UserDeptRltDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-部门关联表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-22
 */
@Mapper
public interface UserDeptRltMapper extends BaseMapper<UserDeptRltDO> {

}
