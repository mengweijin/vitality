package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlNoticeDTO;
import com.github.mengweijin.vitality.system.entity.VtlNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通知记录表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface VtlNoticeMapper extends BaseMapper<VtlNotice> {

    /**
     * Get VtlNotice detail by id
     * @param id id
     */
    VtlNoticeDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlNoticeDTO
     * @return IPage
     */
    IPage<VtlNoticeDTO> page(IPage<VtlNoticeDTO> page, @Param("p") VtlNoticeDTO dto);

}
