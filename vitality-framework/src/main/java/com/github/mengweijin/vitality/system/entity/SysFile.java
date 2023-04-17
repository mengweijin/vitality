package com.github.mengweijin.vitality.system.entity;

import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysFile extends BaseEntity {

    private String fileName;

    private String filePath;

    private String domainUrl;

    private String bucket;
}
