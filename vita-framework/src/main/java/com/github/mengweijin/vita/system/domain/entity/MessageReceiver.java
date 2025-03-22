package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_MESSAGE_RECEIVER")
public class MessageReceiver extends BaseEntity {

    /**
    * 消息ID
    */
    private Long messageId;

    /**
     * 消息接收者用户ID
    */
    private Long userId;

    /**
     * 是否已查看。[Y, N]
    */
    private String viewed;

    /**
     * 查看时间
    */
    private LocalDateTime viewedTime;
}
