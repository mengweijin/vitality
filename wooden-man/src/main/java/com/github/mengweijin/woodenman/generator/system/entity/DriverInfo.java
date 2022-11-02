package com.github.mengweijin.woodenman.generator.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("QBT_GEN_DRIVER")
public class DriverInfo extends BaseEntity {

    private String groupId;

    private String artifactId;

    private String driverVersion;

    private String driverPath;

}
