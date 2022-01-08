package com.github.mengweijin.quickboot.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QB_AUTH")
public class Auth extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限唯一标识关键词。如：system_user_insert, system_user_update
     */
    @TableField("AUTH_KEY")
    private String authKey;

    /**
     * 权限描述。如：用户新增、用户修改、用户查询等
     */
    @TableField("AUTH_NAME")
    private String authName;

}
