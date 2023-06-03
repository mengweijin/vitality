package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 公告管理表
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_ANNOUNCEMENT")
public class VtlAnnouncement extends BaseEntity {

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

}
