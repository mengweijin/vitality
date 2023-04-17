package com.github.mengweijin.vitality.framework.mybatis.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Meng Wei Jin
 * @date Create in 2019-07-28 15:18
 **/
@Data
public abstract class BaseEntity implements Serializable {

    public static final String CREATE_BY_NAME = "createByName";

    public static final String UPDATE_BY_NAME = "updateByName";

    /**
     * 自动生成主键，子类不可重写，但可以在项目中重新定义一个BaseEntity。
     * JsonSerialize：JavaScript 无法处理 Java 的长整型 Long 导致精度丢失，具体表现为主键最后两位永远为 0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    protected Long id;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
    protected Long createBy;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(value = "UPDATE_BY", fill = FieldFill.INSERT_UPDATE)
    protected Long updateBy;

    @TableField(exist = false)
    protected String createByName;

    @TableField(exist = false)
    protected String updateByName;

}
