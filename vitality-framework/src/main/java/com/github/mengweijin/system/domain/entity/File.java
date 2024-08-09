package com.github.mengweijin.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;

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
@Accessors(chain = true)
@TableName("VTL_FILE")
public class File extends BaseEntity {

    /**
    * 原始文件名称
    */
    private String name;

    /**
    * 文件后缀
    */
    private String suffix;

    /**
    * 文件存储路径
    */
    private String storagePath;
}
