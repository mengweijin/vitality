package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.MessageMenuHeaderDataDTO;
import com.github.mengweijin.vitality.system.dto.NoticeDTO;
import com.github.mengweijin.vitality.system.entity.NoticeDO;
import com.github.mengweijin.vitality.system.mapper.NoticeMapper;
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
public class NoticeService extends ServiceImpl<NoticeMapper, NoticeDO> {

    @Autowired
    private NoticeMapper noticeMapper;

    public NoticeDTO detailById(Long id) {
        return noticeMapper.detailById(id);
    }

    public IPage<NoticeDTO> page(IPage<NoticeDTO> page, NoticeDTO dto){
        return noticeMapper.page(page, dto);
    }

    public List<MessageMenuHeaderDataDTO> headerMenuData() {
        NoticeDTO dto = new NoticeDTO();
        dto.setReleased(1);
        IPage<NoticeDTO> page = new Page<>();
        List<NoticeDTO> dtoList = this.page(page, dto).getRecords();

        MessageMenuHeaderDataDTO noticeVO = this.buildMessageHeaderMenuDataVO(dtoList);
        return List.of(noticeVO);
    }

    private MessageMenuHeaderDataDTO buildMessageHeaderMenuDataVO(List<NoticeDTO> list) {
        List<MessageMenuHeaderDataDTO.MessageItemDataVO> itemDataVOList = list.stream()
                .map(MessageMenuHeaderDataDTO.MessageItemDataVO::new)
                .collect(Collectors.toList());

        MessageMenuHeaderDataDTO vo = new MessageMenuHeaderDataDTO();
        vo.setId(IdUtil.simpleUUID());
        vo.setTitle("NOTICE");
        vo.setChildren(itemDataVOList);
        return vo;
    }
}
