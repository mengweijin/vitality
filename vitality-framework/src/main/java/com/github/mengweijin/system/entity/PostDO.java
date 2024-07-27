package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 岗位管理表
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_POST")
public class PostDO extends BaseEntity {

    /**
     * 岗位名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 岗位编码
     */
    @TableField("CODE")
    private String code;

    /**
     * 展示顺序
     */
    @TableField("SEQ")
    private Integer seq;

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
