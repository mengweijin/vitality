package com.github.mengweijin.app.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.app.generator.entity.DatasourceInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author mengweijin
 */
@Mapper
public interface DatasourceMapper extends BaseMapper<DatasourceInfo> {
}
