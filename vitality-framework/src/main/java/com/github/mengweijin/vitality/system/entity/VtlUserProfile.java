package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户头像存储表
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_USER_PROFILE")
public class VtlUserProfile extends BaseEntity {

    /**
     * 用户头像，以 Base64 文本存储的大字段。
     */
    @TableField("PROFILE_PICTURE")
    private String profilePicture;

}
