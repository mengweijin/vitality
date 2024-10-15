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
@TableName("VTL_USER_AVATAR")
public class UserAvatar extends BaseEntity {

    /**
    * 用户ID
    */
    private Long userId;

    /**
    * 用户头像，以 Base64 文本存储的大字段。
    */
    private String avatar;
}
