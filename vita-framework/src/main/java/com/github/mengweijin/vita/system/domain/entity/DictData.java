package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
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
@TableName("VTL_DICT_DATA")
public class DictData extends BaseEntity {

    /**
    * 字典类型编码。
    */
    private String code;

    /**
     * 字典数据值
    */
    private String val;

    /**
    * 字典数据标签名称
    */
    private String label;

    /**
     * 字典数据标签样式。["primary", "success", "warning", "danger", "info"]
     */
    private String tagStyle;

    /**
    * 展示顺序
    */
    private Integer seq;

    /**
    * 是否已禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
