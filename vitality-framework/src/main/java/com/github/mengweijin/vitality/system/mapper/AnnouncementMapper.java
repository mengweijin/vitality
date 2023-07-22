package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.AnnouncementDTO;
import com.github.mengweijin.vitality.system.entity.AnnouncementDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公告管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface AnnouncementMapper extends BaseMapper<AnnouncementDO> {

    /**
     * Get VtlAnnouncement detail by id
     * @param id id
     */
    AnnouncementDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlAnnouncementDTO
     * @return IPage
     */
    IPage<AnnouncementDTO> page(IPage<AnnouncementDTO> page, @Param("p") AnnouncementDTO dto);

}
