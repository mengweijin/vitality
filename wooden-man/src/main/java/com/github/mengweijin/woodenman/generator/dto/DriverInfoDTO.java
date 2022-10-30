package com.github.mengweijin.woodenman.generator.dto;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.File;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DriverInfoDTO extends DriverInfo {

    private String driverName;

    private String driverPath;

    public DriverInfoDTO() {
    }

    public DriverInfoDTO(String groupId, String artifactId, String version) {
        this.setGroupId(groupId);
        this.setArtifactId(artifactId);
        this.setDriverVersion(version);
    }

    public DriverInfoDTO init() {
        this.driverName = jarFileName();
        this.driverPath = driverSavedPath();
        return this;
    }

    public String jarFileName() {
        return this.getArtifactId() + Const.DASH + this.getDriverVersion() + ".jar";
    }

    public String groupPath() {
        return StrUtil.replace(this.getGroupId(), Const.DOT, Const.SLASH);
    }

    public String driverSavedPath() {
        return Const.PROJECT_PATH + "drivers" + File.separator + groupPath() + File.separator + this.getArtifactId() + File.separator + this.getDriverVersion() + File.separator + jarFileName();
    }
}
