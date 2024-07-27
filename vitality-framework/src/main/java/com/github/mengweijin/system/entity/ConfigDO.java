package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 配置管理表
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_CONFIG")
public class ConfigDO extends BaseEntity {

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 配置编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 值
     */
    @TableField("VAL")
    private String val;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

}
