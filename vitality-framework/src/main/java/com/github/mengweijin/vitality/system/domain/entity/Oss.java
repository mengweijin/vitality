package com.github.mengweijin.vitality.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
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
@TableName("VTL_OSS")
public class Oss extends BaseEntity {

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

    /**
     * MD5 码
     */
    private String md5;
}
