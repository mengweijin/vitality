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

    private Integer type;

    private String permission;

    private Integer seq;

    private String icon;

    private String url;

    private String openType;

    private Boolean systemDefault;

    private Boolean disabled;

    private String remark;

}
