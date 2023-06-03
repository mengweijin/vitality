package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通知记录表
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_NOTICE")
public class VtlNotice extends BaseEntity {

    /**
     * 图像链接url
     */
    @TableField("AVATAR")
    private String avatar;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 内容
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 是否已发布。{0=否, 1=是}
     */
    @TableField("RELEASED")
    private Integer released;

    /**
     * 消息接收人范围。比如：全体人员、指定人员、指定部门人员、指定角色人员、指定岗位人员
     */
    @TableField("RECEIVED_RANGE")
    private String receivedRange;

    /**
     * 消息接收者（多个逗号分隔）。根据接收人范围的不同，分别存储：null、用户id、部门id、角色id、岗位id
     */
    @TableField("RECEIVED_CODE")
    private String receivedCode;

}
