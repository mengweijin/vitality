package com.github.mengweijin.vitality.demo.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.demo.enums.Gender;
import com.github.mengweijin.vitality.demo.excel.UserStatusConverter;
import com.github.mengweijin.vitality.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("SYS_USER")
public class User extends BaseEntity {

    @ExcelProperty(value = "姓名", index = 1)
    @TableField("name")
    private String name;

    @ExcelIgnore
    @ExcelProperty(value = "性别", index = 2)
    private Gender gender;

    @ExcelProperty(value = "状态", index = 3, converter = UserStatusConverter.class)
    private Integer deleted;

}
