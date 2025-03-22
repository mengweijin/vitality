package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
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
@TableName("VT_ROLE")
public class Role extends BaseEntity {

    /**
    * 角色名称
    */
    private String name;

    /**
    * 角色编码
    */
    private String code;

    /**
    * 展示顺序
    */
    private Integer seq;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
