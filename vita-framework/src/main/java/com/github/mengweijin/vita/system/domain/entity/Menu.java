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
@TableName("VTL_MENU")
public class Menu extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型。{ MENU=菜单; BTN=按钮; IFRAME=内嵌页面；URL=外链页面；}。前端对应：（0代表菜单、1代表iframe、2代表外链、3代表按钮）
     */
    private String type;

    /**
     * 菜单标题。兼容国际化、非国际化，如果用国际化的写法就必须在根目录的locales文件夹下对应添加
     */
    private String title;

    /**
     * 路由名称。必须唯一并且和当前路由component字段对应的页面里用defineOptions包起来的name保持一致
     */
    private String routerName;

    /**
     * 路由路径。如：/vita/system/user/index
     */
    private String routerPath;

    /**
     * 组件路径。传component组件路径，那么path可以随便写，如果不传，component组件路径会跟path保持一致
     */
    private String componentPath;

    /**
     * 排序。平台规定只有`home`路由的`rank`才能为`0`
     */
    private Integer seq;

    /**
     * 路由重定向。（默认跳转地址）
     */
    private String redirect;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单名称右侧的额外图标
     */
    private String extraIcon;

    /**
     * 进场动画（页面加载动画）
     */
    private String enterTransition;

    /**
     * 离场动画（页面加载动画）
     */
    private String leaveTransition;

    /**
     * 菜单激活（将某个菜单激活，主要用于通过query或params传参的路由，当它们通过配置showLink: false后不在菜单中显示，就不会有任何菜单高亮，而通过设置activePath指定激活菜单即可获得高亮，activePath为指定激活菜单的path）
     */
    private String activePath;

    /**
     * 权限。[*:*:*]
     */
    private String permission;

    /**
     * 配置内嵌页面的 iframe 地址，设置后会在当前页面内嵌对应的页面。
     */
    private String iframeSrc;

    /**
     * [Y, N]。加载动画（内嵌的iframe页面是否开启首次加载动画）。
     */
    private String iframeLoading;

    /**
     * [Y, N]。缓存页面（是否缓存该路由页面，开启后会保存该页面的整体状态，刷新后会清空状态）。
     */
    private String keepAlive;

    /**
     * [Y, N]。标签页（当前菜单名称或自定义信息禁止添加到标签页）。
     */
    private String hiddenTag;

    /**
     * [Y, N]。固定标签页（当前菜单名称是否固定显示在标签页且不可关闭）。
     */
    private String fixedTag;

    /**
     * 是否在菜单中显示。[Y, N]
     */
    private String showLink;

    /**
     * 是否显示父级菜单。[Y, N]
     */
    private String showParent;

}
