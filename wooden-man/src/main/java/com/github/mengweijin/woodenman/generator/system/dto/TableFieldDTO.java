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
    /**
     * 是否为主键
     */
    private boolean keyFlag;

    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 属性类型
     */
    private DbColumnType columnType;

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 列注释
     */
    private String comment;




}
