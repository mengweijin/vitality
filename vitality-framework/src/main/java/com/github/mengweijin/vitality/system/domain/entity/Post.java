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
@TableName("VTL_POST")
public class Post extends BaseEntity {

    /**
    * 岗位名称
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
    * 备注
    */
    private String remark;
}
