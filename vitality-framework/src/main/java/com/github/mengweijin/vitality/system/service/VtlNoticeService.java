package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlMessageMenuHeaderDataDTO;
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

    public List<VtlMessageMenuHeaderDataDTO> headerMenuData() {
        VtlNoticeDTO dto = new VtlNoticeDTO();
        dto.setReleased(1);
        IPage<VtlNoticeDTO> page = new Page<>();
        List<VtlNoticeDTO> dtoList = this.page(page, dto).getRecords();

        VtlMessageMenuHeaderDataDTO noticeVO = this.buildMessageHeaderMenuDataVO(dtoList);
        return List.of(noticeVO);
    }

    private VtlMessageMenuHeaderDataDTO buildMessageHeaderMenuDataVO(List<VtlNoticeDTO> list) {
        List<VtlMessageMenuHeaderDataDTO.MessageItemDataVO> itemDataVOList = list.stream()
                .map(VtlMessageMenuHeaderDataDTO.MessageItemDataVO::new)
                .collect(Collectors.toList());

        VtlMessageMenuHeaderDataDTO vo = new VtlMessageMenuHeaderDataDTO();
        vo.setId(IdUtil.simpleUUID());
        vo.setTitle("NOTICE");
        vo.setChildren(itemDataVOList);
        return vo;
    }
}
