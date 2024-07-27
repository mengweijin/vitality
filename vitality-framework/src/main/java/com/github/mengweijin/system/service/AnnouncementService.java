package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.entity.AnnouncementDO;
import com.github.mengweijin.system.mapper.AnnouncementMapper;
import com.github.mengweijin.system.dto.AnnouncementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公告管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Service
public class AnnouncementService extends ServiceImpl<AnnouncementMapper, AnnouncementDO> {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public AnnouncementDTO detailById(Long id) {
        return announcementMapper.detailById(id);
    }

    public IPage<AnnouncementDTO> page(IPage<AnnouncementDTO> page, AnnouncementDTO dto){
        return announcementMapper.page(page, dto);
    }
}
