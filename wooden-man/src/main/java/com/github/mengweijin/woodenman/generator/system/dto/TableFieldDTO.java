package com.github.mengweijin.woodenman.generator.system.dto;

import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/11/6
 */
@Data
public class TableFieldDTO implements Serializable {

    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;

    /**
     * 列名
     */
    private String name;

    private String type;

    private DbColumnType columnType;

    /**
     * 属性名
     */
    private String propertyName;

    private String comment;

    private String fill;

    private String columnName;

}
