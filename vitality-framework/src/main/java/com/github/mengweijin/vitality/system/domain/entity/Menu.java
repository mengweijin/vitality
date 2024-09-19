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
@TableName("VTL_MENU")
public class Menu extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮; IFRAME=内嵌页面；URL=外链页面；}
     */
    private String type;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单名称右侧的额外图标
     */
    private String extraIcon;

    /**
     * 是否在菜单中显示。[Y, N]
     */
    private String showLink;

    /**
     * 权限
     */
    private String permission;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 路由名称，必须唯一。如：UserListIndex
     */
    private String routerName;

    /**
     * 路由地址。如：/vitality/system/user/index
     */
    private String routerPath;

    /**
     * 配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面。
     */
    private String iframe;

    /**
     * 是否禁用。[Y, N]
     */
    private String disabled;

    /**
     * 备注
     */
    private String remark;
}
