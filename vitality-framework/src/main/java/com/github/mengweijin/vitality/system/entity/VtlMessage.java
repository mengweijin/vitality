package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.system.enums.EMessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_MESSAGE")
public class VtlMessage extends BaseEntity {

    private EMessageType type;

    private String avatar;

    private String title;

    private String description;

    private Boolean released;

    private Boolean confirmed;

    private Boolean handled;

    private String urlLink;
}
