package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 *
 * @author Meng Wei Jin
 * @description
 * @date Create in 2019-07-28 15:18
 **/
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 自动生成主键，子类不可重写，但可以在项目中重新定义一个BaseEntity。
     * JsonSerialize：JavaScript 无法处理 Java 的长整型 Long 导致精度丢失，具体表现为主键最后两位永远为 0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected ZonedDateTime createTime;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected String createBy;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected ZonedDateTime updateTime;

    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 逻辑删除
     */
    @TableLogic
    protected Integer deleted;

    /**
     * 乐观锁。不是每张表都需要
     */
    //@Version
    //protected Long version;
}
