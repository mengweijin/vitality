package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.domain.entity.Message;
import com.github.mengweijin.vitality.system.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  Message Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    /**
     * Custom paging query
     * @param page page
     * @param message {@link Message}
     * @return IPage
     */
    public IPage<Message> page(IPage<Message> page, Message message){
        LambdaQueryWrapper<Message> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(message.getName()), Message::getName, message.getName())
                .eq(StrUtil.isNotBlank(message.getDescription()), Message::getDescription, message.getDescription())
                .eq(!Objects.isNull(message.getId()), Message::getId, message.getId())
                .eq(!Objects.isNull(message.getCreateBy()), Message::getCreateBy, message.getCreateBy())
                .eq(!Objects.isNull(message.getCreateTime()), Message::getCreateTime, message.getCreateTime())
                .eq(!Objects.isNull(message.getUpdateBy()), Message::getUpdateBy, message.getUpdateBy())
                .eq(!Objects.isNull(message.getUpdateTime()), Message::getUpdateTime, message.getUpdateTime());
        return this.page(page, query);
    }
}
