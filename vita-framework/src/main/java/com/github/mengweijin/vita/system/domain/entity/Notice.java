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
