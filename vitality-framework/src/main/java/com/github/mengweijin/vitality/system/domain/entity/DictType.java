package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
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
@TableName("VTL_DICT_TYPE")
public class DictType extends BaseEntity {

    /**
    * 字典名称
    */
    private String name;

    /**
    * 字典类型编码。
    */
    private String code;

    /**
    * 备注
    */
    private String remark;
}
