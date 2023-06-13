package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlFileDTO;
import com.github.mengweijin.vitality.system.entity.VtlFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface VtlFileMapper extends BaseMapper<VtlFile> {

    /**
     * Get VtlFile detail by id
     * @param id id
     */
    VtlFileDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlFileDTO
     * @return IPage
     */
    IPage<VtlFileDTO> page(IPage<VtlFileDTO> page, @Param("p") VtlFileDTO dto);

}
