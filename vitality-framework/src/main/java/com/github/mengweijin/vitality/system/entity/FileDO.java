package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统文件表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_FILE")
public class FileDO extends BaseEntity {

    /**
     * 原始文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 文件后缀
     */
    @TableField("FILE_SUFFIX")
    private String fileSuffix;

    /**
     * minio 文件存储全路径
     */
    @TableField("FILE_PATH")
    private String filePath;

    /**
     * minio bucket name
     */
    @TableField("BUCKET")
    private String bucket;

}
