package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典类型表
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_DICT_TYPE")
public class DictTypeDO extends BaseEntity {

    /**
     * 字典名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 字典类型编码。
     */
    @TableField("TYPE_CODE")
    private String typeCode;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

}
