package com.github.mengweijin.quickboot.auth.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色和用户关联表
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QB_USER_ROLE")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

}
