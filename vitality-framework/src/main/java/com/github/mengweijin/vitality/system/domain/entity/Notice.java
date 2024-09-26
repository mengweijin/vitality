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
@TableName("VTL_NOTICE")
public class Notice extends BaseEntity {

    /**
    * 名称
    */
    private String name;

    /**
    * 内容
    */
    private String description;

    /**
    * 是否已发布。[Y, N]
    */
    private String released;
}
