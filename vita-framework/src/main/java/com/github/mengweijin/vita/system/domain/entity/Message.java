package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_MESSAGE")
public class Message extends BaseEntity {

    /**
     * 消息分类。{@link EMessageCategory}
     */
    private String category;

    /**
    * 名称
    */
    private String title;

    /**
    * 内容
    */
    private String content;
}
