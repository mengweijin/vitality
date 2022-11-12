package com.github.mengweijin.woodenman.generator.system.dto;

import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
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
     * 列类型。实际的 java 对象为 DbColumnType.java 的实例
     */
    private IColumnType columnType;

    /**
     * Entity 字段属性 Java 类型
     */
    private String propertyType;

    /**
     * Entity 字段属性名
     */
    private String propertyName;

    /**
     * Entity 字段属性 Java 类型对应的 import package 路径
     */
    private String propertyTypePackage;

    /**
     * 列注释
     */
    private String comment;

    /**
     * 是否在 Entity.java 中忽略该字段
     */
    private boolean entityIgnored;

}
