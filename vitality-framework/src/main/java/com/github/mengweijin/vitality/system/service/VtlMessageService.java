package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlMessageDTO;
import com.github.mengweijin.vitality.system.dto.VtlMessageHeaderMenuDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMessage;
import com.github.mengweijin.vitality.system.enums.EMessageType;
import com.github.mengweijin.vitality.system.mapper.VtlMessageMapper;
import org.dromara.hutool.core.data.id.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class VtlMessageService extends ServiceImpl<VtlMessageMapper, VtlMessage> {

    @Autowired
    private VtlDictDataService dictDataService;

    @Autowired
    private VtlMessageMapper vtlMessageMapper;

    public IPage<VtlMessageDTO> page(IPage<VtlMessageDTO> page, VtlMessageDTO dto){
        return vtlMessageMapper.page(page, dto);
    }

    public List<VtlMessageDTO> getFirstPageHeaderMenuDataByType(EMessageType type) {
        VtlMessageDTO dto = new VtlMessageDTO();
        dto.setType(type);
        dto.setReleased(1);
        if(EMessageType.BACKLOG == type) {
            dto.setHandled(0);
        }
        IPage<VtlMessageDTO> page = new Page<>();
        return this.page(page, dto).getRecords();
    }

    public List<VtlMessageHeaderMenuDataDTO> headerMenuData() {
        List<VtlMessageDTO> noticeList = this.getFirstPageHeaderMenuDataByType(EMessageType.NOTICE);
        List<VtlMessageDTO> announcementList = this.getFirstPageHeaderMenuDataByType(EMessageType.ANNOUNCEMENT);
        List<VtlMessageDTO> backlogList = this.getFirstPageHeaderMenuDataByType(EMessageType.BACKLOG);

        VtlMessageHeaderMenuDataDTO noticeVO = this.buildMessageHeaderMenuDataVO(EMessageType.NOTICE, noticeList);
        VtlMessageHeaderMenuDataDTO announcementVO = this.buildMessageHeaderMenuDataVO(EMessageType.ANNOUNCEMENT, announcementList);
        VtlMessageHeaderMenuDataDTO backlogVO = this.buildMessageHeaderMenuDataVO(EMessageType.BACKLOG, backlogList);
        return Arrays.asList(noticeVO, announcementVO, backlogVO);
    }

    private VtlMessageHeaderMenuDataDTO buildMessageHeaderMenuDataVO(EMessageType messageType, List<VtlMessageDTO> list) {
        List<VtlMessageHeaderMenuDataDTO.MessageItemDataVO> itemDataVOList = list.stream()
                .map(VtlMessageHeaderMenuDataDTO.MessageItemDataVO::new)
                .collect(Collectors.toList());

        VtlMessageHeaderMenuDataDTO vo = new VtlMessageHeaderMenuDataDTO();
        vo.setId(IdUtil.simpleUUID());
        vo.setTitle(messageType.getValue());
        vo.setChildren(itemDataVOList);
        return vo;
    }

    public VtlMessageDTO detail(Long id) {
        return vtlMessageMapper.detail(id);
    }
}
