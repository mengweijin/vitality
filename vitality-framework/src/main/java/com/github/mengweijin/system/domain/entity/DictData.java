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
@TableName("VTL_DICT_DATA")
public class DictData extends BaseEntity {

    /**
    * 字典类型编码。
    */
    private String typeCode;

    /**
    * 字典数据编码。
    */
    private String dataCode;

    /**
    * 字典数据标签名称
    */
    private String label;

    /**
    * 展示顺序
    */
    private Integer seq;

    /**
    * 是否设置为默认选择项。[Y, N]
    */
    private String selected;

    /**
    * 是否已禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
