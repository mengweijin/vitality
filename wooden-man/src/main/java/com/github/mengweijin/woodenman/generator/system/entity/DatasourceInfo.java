package com.github.mengweijin.woodenman.generator.system.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("QBT_GEN_DATASOURCE")
public class DatasourceInfo extends BaseEntity {

    @TableField("db_type")
    private DbType dbType;

    private String url;

    private String username;

    private String password;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long driverId;

    private Boolean autoRefreshDriver;
}
