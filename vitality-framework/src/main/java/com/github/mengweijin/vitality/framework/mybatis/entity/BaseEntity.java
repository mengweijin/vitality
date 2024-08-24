package com.github.mengweijin.vitality.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.mengweijin.vitality.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vitality.framework.jackson.translation.Translation;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Meng Wei Jin
 * @since 2019-07-28
 **/
@Data
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    protected Long id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @TableField(value = "UPDATE_TIME", fill = FieldFill.UPDATE)
    protected LocalDateTime updateTime;

    @TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
    protected Long createBy;

    @TableField(value = "UPDATE_BY", fill = FieldFill.UPDATE)
    protected Long updateBy;

    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "createBy")
    private String createByName;

    @TableField(exist = false)
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME, field = "updateBy")
    private String updateByName;

}
