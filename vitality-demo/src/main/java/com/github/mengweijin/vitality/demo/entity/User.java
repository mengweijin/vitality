package com.github.mengweijin.vitality.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.demo.enums.Gender;
import com.github.mengweijin.vitality.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_USER")
public class User extends BaseEntity {

    @TableField("name")
    private String name;

    private Gender gender;

    private Integer deleted;

}
