package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门管理表
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_DEPT")
public class DeptDO extends BaseEntity {

    /**
     * 父部门ID
     */
    @TableField("PARENT_ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 祖先层级ID，以"/" 分隔。
     */
    @TableField("ANCESTORS")
    private String ancestors;

    /**
     * 部门名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 部门编码
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
