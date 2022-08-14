package com.github.mengweijin.generator.system.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * QBG_DB
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QBG_DB")
public class Db extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Refer to ${@link com.baomidou.mybatisplus.annotation.DbType}
     */
    @TableField("db_type")
    private DbType dbType;

    @TableField(value = "URL", condition = SqlCondition.LIKE)
    private String url;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;



}
