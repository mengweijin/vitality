package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlAnnouncement;
import com.github.mengweijin.vitality.system.mapper.VtlAnnouncementMapper;
import com.github.mengweijin.vitality.system.dto.VtlAnnouncementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公告管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Service
public class VtlAnnouncementService extends ServiceImpl<VtlAnnouncementMapper, VtlAnnouncement> {

    @Autowired
    private VtlAnnouncementMapper vtlAnnouncementMapper;

    public VtlAnnouncementDTO detailById(Long id) {
        return vtlAnnouncementMapper.detailById(id);
    }

    public IPage<VtlAnnouncementDTO> page(IPage<VtlAnnouncementDTO> page, VtlAnnouncementDTO dto){
        return vtlAnnouncementMapper.page(page, dto);
    }
}
