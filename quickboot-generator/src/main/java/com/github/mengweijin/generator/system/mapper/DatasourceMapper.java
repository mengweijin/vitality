package com.github.mengweijin.generator.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.generator.system.entity.GenDatasource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mengweijin
 */
@Mapper
public interface DatasourceMapper extends BaseMapper<GenDatasource> {
}
