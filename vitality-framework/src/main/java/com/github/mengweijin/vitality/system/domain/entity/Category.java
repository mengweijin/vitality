package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.validator.rule.CategoryCodeDuplicateCheckRule;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_CATEGORY")
public class Category extends BaseEntity {

    /**
     * PARENT ID
     */
    private Long parentId;

    /**
     * 编码
     */
    @NotBlank(groups = Group.Create.class)
    @BusinessCheck(groups = Group.Create.class, checkRule = CategoryCodeDuplicateCheckRule.class)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 展示顺序
     */
    private Integer seq;

    /**
     * 是否已禁用。[Y, N]
     */
    private String disabled;
}
