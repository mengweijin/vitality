package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlLogErrorDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogError;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统错误日志记录表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Mapper
public interface VtlLogErrorMapper extends BaseMapper<VtlLogError> {

    /**
     * Get VtlLogError detail by id
     * @param id id
     */
    VtlLogErrorDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlLogErrorDTO
     * @return IPage
     */
    IPage<VtlLogErrorDTO> page(IPage<VtlLogErrorDTO> page, @Param("p") VtlLogErrorDTO dto);

}
