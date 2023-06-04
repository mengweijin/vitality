package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlConfigDTO;
import com.github.mengweijin.vitality.system.entity.VtlConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 配置管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@Mapper
public interface VtlConfigMapper extends BaseMapper<VtlConfig> {

    /**
     * Get VtlConfig detail by id
     * @param id id
     */
    VtlConfigDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlConfigDTO
     * @return IPage
     */
    IPage<VtlConfigDTO> page(IPage<VtlConfigDTO> page, @Param("p") VtlConfigDTO dto);

}
