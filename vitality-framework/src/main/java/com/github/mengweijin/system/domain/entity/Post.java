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
@TableName("VTL_POST")
public class Post extends BaseEntity {

    /**
    * 岗位名称
    */
    private String name;

    /**
    * 岗位编码
    */
    private String code;

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
