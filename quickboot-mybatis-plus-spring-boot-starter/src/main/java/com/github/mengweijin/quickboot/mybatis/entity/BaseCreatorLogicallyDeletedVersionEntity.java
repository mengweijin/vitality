package com.github.mengweijin.quickboot.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Meng Wei Jin
 * @date Create in 2019-07-28 15:18
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseCreatorLogicallyDeletedVersionEntity extends BaseCreatorLogicallyDeletedEntity {

    /**
     * 乐观锁。不是每张表都需要
     */
    @Version
    protected Long version;
}
