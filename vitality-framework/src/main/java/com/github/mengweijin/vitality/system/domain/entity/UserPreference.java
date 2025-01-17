package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("VTL_USER_PREFERENCE")
public class UserPreference extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 其他用户的消息是否将以站内信的形式通知。[Y, N]
     */
    private String userMessage;

    /**
     * 系统消息是否将以站内信的形式通知。[Y, N]
     */
    private String systemMessage;

    /**
     * 待办任务是否将以站内信的形式通知。[Y, N]
     */
    private String todoTask;
}
