package com.github.mengweijin.woodenman.generator.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("QBT_GEN_TEMPLATE")
public class Template extends BaseEntity {

    private String category;

    private String name;

    private String content;

    private String suffix;

    private Boolean builtIn;

}
