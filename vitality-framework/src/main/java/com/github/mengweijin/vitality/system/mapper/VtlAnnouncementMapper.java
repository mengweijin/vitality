package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlAnnouncementDTO;
import com.github.mengweijin.vitality.system.entity.VtlAnnouncement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公告管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface VtlAnnouncementMapper extends BaseMapper<VtlAnnouncement> {

    /**
     * Get VtlAnnouncement detail by id
     * @param id id
     */
    VtlAnnouncementDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlAnnouncementDTO
     * @return IPage
     */
    IPage<VtlAnnouncementDTO> page(IPage<VtlAnnouncementDTO> page, @Param("p") VtlAnnouncementDTO dto);

}
