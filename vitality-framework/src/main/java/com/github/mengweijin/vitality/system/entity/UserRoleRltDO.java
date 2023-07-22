package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户-角色关联表
 *
 * @author mengweijin
 * @since 2023-06-22
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_USER_ROLE_RLT")
public class UserRoleRltDO extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("USER_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

}
