package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.validator.rule.CategoryCodeDuplicateCheckRule;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_CATEGORY")
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
