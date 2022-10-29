package com.github.mengweijin.woodenman.generator.domain;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.util.Const;
import lombok.Data;

import java.io.File;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Data
public class JarInfo {
    private String groupId;
    private String artifactId;
    private String version;

    public JarInfo(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getJarName() {
        return artifactId + Const.DASH + version + ".jar";
    }

    public String getGroupPath() {
        return StrUtil.replace(groupId, Const.DOT, Const.SLASH);
    }

    public String getSavePath() {
        return Const.PROJECT_PATH + "drivers" + File.separator + getGroupPath() + File.separator + artifactId + File.separator + version + File.separator + getJarName();
    }
}
