package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlNoticeDTO;
import com.github.mengweijin.vitality.system.entity.VtlNotice;
import com.github.mengweijin.vitality.system.mapper.VtlNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知记录表 服务类
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Service
public class VtlNoticeService extends ServiceImpl<VtlNoticeMapper, VtlNotice> {

    @Autowired
    private VtlNoticeMapper vtlNoticeMapper;

    public VtlNoticeDTO detailById(Long id) {
        return vtlNoticeMapper.detailById(id);
    }

    public IPage<VtlNoticeDTO> page(IPage<VtlNoticeDTO> page, VtlNoticeDTO dto){
        return vtlNoticeMapper.page(page, dto);
    }
}
