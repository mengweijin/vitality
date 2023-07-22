package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典数据表
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_DICT_DATA")
public class DictDataDO extends BaseEntity {

    /**
     * 字典类型编码。
     */
    @TableField("TYPE_CODE")
    private String typeCode;

    /**
     * 字典数据编码。
     */
    @TableField("DATA_CODE")
    private String dataCode;

    /**
     * 字典数据标签名称
     */
    @TableField("LABEL")
    private String label;

    /**
     * 展示顺序
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 是否设置为默认选择项。{ 0：否；1：是；}
     */
    @TableField("DEFAULT_SELECTED")
    private Integer defaultSelected;

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
