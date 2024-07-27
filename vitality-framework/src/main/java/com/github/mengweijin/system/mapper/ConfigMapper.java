package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.system.dto.ConfigDTO;
import com.github.mengweijin.system.entity.ConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 配置管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@Mapper
public interface ConfigMapper extends BaseMapper<ConfigDO> {

    /**
     * Get VtlConfig detail by id
     * @param id id
     */
    ConfigDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlConfigDTO
     * @return IPage
     */
    IPage<ConfigDTO> page(IPage<ConfigDTO> page, @Param("p") ConfigDTO dto);

}
