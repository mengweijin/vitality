package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
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
@TableName("VTL_FILE")
public class VtlFile extends BaseEntity {

    @TableField("FILE_NAME")
    private String fileName;

    @TableField("FILE_PATH")
    private String filePath;

    @TableField("DOMAIN_URL")
    private String domainUrl;

    @TableField("BUCKET")
    private String bucket;
}
