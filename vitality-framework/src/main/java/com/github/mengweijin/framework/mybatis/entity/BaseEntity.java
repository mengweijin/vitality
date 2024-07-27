package com.github.mengweijin.framework.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 创建者昵称
     */
    @TableField(exist = false)
    private String createByName;

    /**
     * 更新者昵称
     */
    @TableField(exist = false)
    private String updateByName;

    /**
     * 搜索值
     */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
