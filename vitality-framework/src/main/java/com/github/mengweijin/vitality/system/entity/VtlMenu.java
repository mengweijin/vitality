package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_MENU")
public class VtlMenu extends BaseEntity {

    private Long parentId;

    private String title;

    /**
     * 菜单类型。{ 0=目录; 1=菜单; 2=按钮; 3=其它 }
     */
    private Integer type;

    /**
     * 权限标识,唯一性约束。
     */
    private String permission;

    private Integer seq;

    private String icon;

    private String url;

    /**
     * 菜单打开类型。当 type 为 1 时，openType 生效，{ _iframe：正常打开；_blank：新建浏览器标签页 }
     */
    private String openType;

    private Boolean systemDefault;

    private Boolean disabled;

    private String remark;

}
