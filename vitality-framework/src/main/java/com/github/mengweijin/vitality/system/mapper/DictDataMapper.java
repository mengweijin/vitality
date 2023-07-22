package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.DictDataDTO;
import com.github.mengweijin.vitality.system.entity.DictDataDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典数据表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictDataDO> {

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlDictDataDTO
     * @return IPage
     */
    IPage<DictDataDTO> page(IPage<DictDataDTO> page, @Param("p") DictDataDTO dto);

}
