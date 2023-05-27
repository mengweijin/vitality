package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlMessage;
import com.github.mengweijin.vitality.system.enums.EMessageType;
import com.github.mengweijin.vitality.system.mapper.VtlMessageMapper;
import com.github.mengweijin.vitality.system.dto.VtlMessageHeaderMenuDataDTO;
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

    public List<VtlMessageHeaderMenuDataDTO> headerMenuData() {
        List<VtlMessage> noticeList = this.lambdaQuery()
                .eq(VtlMessage::getType, EMessageType.NOTICE)
                .eq(VtlMessage::getReleased, true)
                .eq(VtlMessage::getConfirmed, false)
                .list();

        List<VtlMessage> backlogList = this.lambdaQuery()
                .eq(VtlMessage::getType, EMessageType.BACKLOG)
                .eq(VtlMessage::getReleased, true)
                .eq(VtlMessage::getHandled, false)
                .list();

        VtlMessageHeaderMenuDataDTO noticeVO = this.buildMessageHeaderMenuDataVO(1L, EMessageType.NOTICE, noticeList);
        VtlMessageHeaderMenuDataDTO backlogVO = this.buildMessageHeaderMenuDataVO(2L, EMessageType.BACKLOG, backlogList);
        return Arrays.asList(noticeVO, backlogVO);
    }

    private VtlMessageHeaderMenuDataDTO buildMessageHeaderMenuDataVO(Long tabId, EMessageType messageType, List<VtlMessage> list) {
        List<VtlMessageHeaderMenuDataDTO.MessageItemDataVO> itemDataVOList = list.stream()
                .map(VtlMessageHeaderMenuDataDTO.MessageItemDataVO::new)
                .collect(Collectors.toList());

        VtlMessageHeaderMenuDataDTO vo = new VtlMessageHeaderMenuDataDTO();
        vo.setId(tabId);
        vo.setTitle(messageType.getValue());
        vo.setChildren(itemDataVOList);
        return vo;
    }
}
