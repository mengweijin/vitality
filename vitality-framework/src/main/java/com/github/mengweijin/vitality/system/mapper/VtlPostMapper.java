package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlPostDTO;
import com.github.mengweijin.vitality.system.entity.VtlPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 岗位管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Mapper
public interface VtlPostMapper extends BaseMapper<VtlPost> {

    /**
     * Get VtlPost detail by id
     * @param id id
     */
    VtlPostDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlPostDTO
     * @return IPage
     */
    IPage<VtlPostDTO> page(IPage<VtlPostDTO> page, @Param("p") VtlPostDTO dto);

}
