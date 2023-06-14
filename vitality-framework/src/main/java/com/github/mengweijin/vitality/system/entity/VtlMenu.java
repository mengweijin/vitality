package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.system.enums.EMenuOpenType;
import com.github.mengweijin.vitality.system.enums.EMenuType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单及权限配置表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_MENU")
public class VtlMenu extends BaseEntity {

    /**
     * 父菜单ID
     */
    @TableField("PARENT_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 菜单类型。{ DIR=目录; MENU=菜单; BTN=按钮; OTHER=其它 }
     */
    @TableField("TYPE")
    private EMenuType type;

    /**
     * 权限标识,唯一性约束。
     */
    @TableField("PERMISSION")
    private String permission;

    /**
     * 展示顺序
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 菜单图标
     */
    @TableField("ICON")
    private String icon;

    /**
     * 菜单请求链接地址。当 type 为 MENU 时生效。
     */
    @TableField("URL")
    private String url;

    /**
     * 菜单打开类型。当 type 为 MENU 时，openType 生效，{ _iframe：正常打开；_blank：新建浏览器标签页 }
     */
    @TableField("OPEN_TYPE")
    private EMenuOpenType openType;

    /**
     * 是否已禁用。{ 0：正常；1：禁用；}
     */
    @TableField("DISABLED")
    private Integer disabled;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

}
