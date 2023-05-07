package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.framework.mybatis.data.username.QueryUserName;
import com.github.mengweijin.vitality.system.entity.VtlErrorLog;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface VtlErrorLogMapper extends BaseMapper<VtlErrorLog> {

    @QueryUserName
    @Override
    VtlErrorLog selectById(Serializable id);

}
