package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    * 消息接收用户（登录名）
    */
    private String receiverUser;

    /**
    * 消息接收角色（编码）
    */
    private String receiverRole;

    /**
    * 消息接收部门（编码）
    */
    private String receiverDept;
}
