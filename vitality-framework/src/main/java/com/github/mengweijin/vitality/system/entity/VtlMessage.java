package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.system.enums.EMessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统消息（通知，待办）记录表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_MESSAGE")
public class VtlMessage extends BaseEntity {

    /**
     * 消息类型。{ NOTICE("通知"), ANNOUNCEMENT("公告"), BACKLOG("待办") }
     */
    @TableField("TYPE")
    private EMessageType type;

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
     * 是否已确认。{0=否, 1=是}
     */
    @TableField("CONFIRMED")
    private Integer confirmed;

    /**
     * 是否已处理。{0=否, 1=是}
     */
    @TableField("HANDLED")
    private Integer handled;

    /**
     * 跳转URL链接
     */
    @TableField("URL_LINK")
    private String urlLink;

}
