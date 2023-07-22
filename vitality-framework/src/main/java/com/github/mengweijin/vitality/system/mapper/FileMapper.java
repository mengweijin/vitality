package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.FileDTO;
import com.github.mengweijin.vitality.system.entity.FileDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface FileMapper extends BaseMapper<FileDO> {

    /**
     * Get VtlFile detail by id
     * @param id id
     */
    FileDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlFileDTO
     * @return IPage
     */
    IPage<FileDTO> page(IPage<FileDTO> page, @Param("p") FileDTO dto);

}
