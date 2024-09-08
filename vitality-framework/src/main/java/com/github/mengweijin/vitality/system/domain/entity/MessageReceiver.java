package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("VTL_MESSAGE_RECEIVER")
public class MessageReceiver extends BaseEntity {

    /**
    * 消息ID
    */
    private Long messageId;

    /**
     * 消息接收者用户ID
    */
    private String receiverId;

    /**
     * 是否已查看。[Y, N]
    */
    private String viewed;

    /**
     * 查看时间
    */
    private LocalDateTime viewedTime;
}
