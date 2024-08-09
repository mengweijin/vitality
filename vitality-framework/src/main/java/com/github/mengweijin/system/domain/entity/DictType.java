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
@TableName("VTL_DICT_TYPE")
public class DictType extends BaseEntity {

    /**
    * 字典名称
    */
    private String name;

    /**
    * 字典类型编码。
    */
    private String typeCode;

    /**
    * 备注
    */
    private String remark;
}
