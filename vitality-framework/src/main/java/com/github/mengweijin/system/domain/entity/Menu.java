package com.github.mengweijin.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;

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
@TableName("VTL_MENU")
public class Menu extends BaseEntity {

    /**
    * 父菜单ID
    */
    private Long parentId;

    /**
    * 菜单标题
    */
    private String title;

    /**
    * 菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮 }
    */
    private String type;

    /**
    * 权限标识
    */
    private String permission;

    /**
    * 展示顺序
    */
    private Integer seq;

    /**
    * 菜单图标
    */
    private String icon;

    /**
    * 菜单请求链接地址。当 type 为 MENU 时生效。
    */
    private String url;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
