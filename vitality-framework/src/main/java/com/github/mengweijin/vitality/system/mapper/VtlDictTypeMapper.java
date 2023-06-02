package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlDictTypeDTO;
import com.github.mengweijin.vitality.system.entity.VtlDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Mapper
public interface VtlDictTypeMapper extends BaseMapper<VtlDictType> {

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlDictTypeDTO
     * @return IPage
     */
    IPage<VtlDictTypeDTO> page(IPage<VtlDictTypeDTO> page, @Param("p") VtlDictTypeDTO dto);

}
