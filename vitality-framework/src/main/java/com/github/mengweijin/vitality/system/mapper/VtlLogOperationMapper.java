package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlLogOperationDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogOperation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统操作日志表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Mapper
public interface VtlLogOperationMapper extends BaseMapper<VtlLogOperation> {

    /**
     * Get VtlLogOperation detail by id
     * @param id id
     */
    VtlLogOperationDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlLogOperationDTO
     * @return IPage
     */
    IPage<VtlLogOperationDTO> page(IPage<VtlLogOperationDTO> page, @Param("p") VtlLogOperationDTO dto);

}
