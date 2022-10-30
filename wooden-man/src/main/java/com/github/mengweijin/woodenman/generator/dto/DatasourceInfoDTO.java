package com.github.mengweijin.woodenman.generator.dto;

import com.github.mengweijin.woodenman.generator.entity.DatasourceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DatasourceInfoDTO extends DatasourceInfo {

    private String groupId;

    private String artifactId;

    private String driverVersion;

    private String driverPath;
}
