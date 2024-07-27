package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.system.dto.PostDTO;
import com.github.mengweijin.system.entity.PostDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 岗位管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Mapper
public interface PostMapper extends BaseMapper<PostDO> {

    /**
     * Get VtlPost detail by id
     * @param id id
     */
    PostDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlPostDTO
     * @return IPage
     */
    IPage<PostDTO> page(IPage<PostDTO> page, @Param("p") PostDTO dto);

    List<PostDTO> getByUserId(Long userId);
}
