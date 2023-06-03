package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlMessageHeaderMenuDataDTO;
import com.github.mengweijin.vitality.system.dto.VtlNoticeDTO;
import com.github.mengweijin.vitality.system.entity.VtlNotice;
import com.github.mengweijin.vitality.system.mapper.VtlNoticeMapper;
import org.dromara.hutool.core.data.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<VtlMessageHeaderMenuDataDTO> headerMenuData() {
        VtlNoticeDTO dto = new VtlNoticeDTO();
        dto.setReleased(1);
        IPage<VtlNoticeDTO> page = new Page<>();
        List<VtlNoticeDTO> dtoList = this.page(page, dto).getRecords();

        VtlMessageHeaderMenuDataDTO noticeVO = this.buildMessageHeaderMenuDataVO(dtoList);
        return List.of(noticeVO);
    }

    private VtlMessageHeaderMenuDataDTO buildMessageHeaderMenuDataVO(List<VtlNoticeDTO> list) {
        List<VtlMessageHeaderMenuDataDTO.MessageItemDataVO> itemDataVOList = list.stream()
                .map(VtlMessageHeaderMenuDataDTO.MessageItemDataVO::new)
                .collect(Collectors.toList());

        VtlMessageHeaderMenuDataDTO vo = new VtlMessageHeaderMenuDataDTO();
        vo.setId(IdUtil.simpleUUID());
        vo.setTitle("NOTICE");
        vo.setChildren(itemDataVOList);
        return vo;
    }
}
