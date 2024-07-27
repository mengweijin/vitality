package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.system.dto.DictTypeDTO;
import com.github.mengweijin.system.entity.DictTypeDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典类型表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Mapper
public interface DictTypeMapper extends BaseMapper<DictTypeDO> {

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlDictTypeDTO
     * @return IPage
     */
    IPage<DictTypeDTO> page(IPage<DictTypeDTO> page, @Param("p") DictTypeDTO dto);

}
