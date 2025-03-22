package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vita.framework.validator.annotation.BusinessCheck;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.validator.rule.DictTypeCodeDuplicateCheckRule;
import jakarta.validation.constraints.NotBlank;
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
@TableName("VT_DICT_TYPE")
public class DictType extends BaseEntity {

    /**
    * 字典名称
    */
    @NotBlank
    private String name;

    /**
    * 字典类型编码。
    */
    @NotBlank(groups = Group.Create.class)
    @BusinessCheck(groups = Group.Create.class, checkRule = DictTypeCodeDuplicateCheckRule.class)
    private String code;

    /**
    * 备注
    */
    private String remark;
}
