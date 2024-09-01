package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("VTL_DEPT")
public class Dept extends BaseEntity {

    /**
    * 父部门ID
    */
    private Long parentId;

    /**
    * 部门名称
    */
    private String name;

    /**
    * 展示顺序
    */
    private Integer seq;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
     * 逻辑删除。[Y, N]
     */
    @TableLogic
    private String deleted;

    /**
    * 备注
    */
    private String remark;
}
