package com.github.mengweijin.woodenman.generator.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.mapper.DriverMapper;
import com.github.mengweijin.woodenman.generator.util.MavenJarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DriverService extends ServiceImpl<DriverMapper, DriverInfo> {

    @Autowired
    private DriverMapper driverMapper;

    public boolean downloadAndUpdate(DriverInfo driverInfo) {
        String path = MavenJarUtils.downloadJar(driverInfo.getGroupId(), driverInfo.getArtifactId(), driverInfo.getDriverVersion());
        return this.lambdaUpdate()
                .set(DriverInfo::getDriverPath, path)
                .eq(DriverInfo::getId, driverInfo.getId())
                .update();
    }

    public DriverInfo getMaxVersionDriverInfoByGroupIdAndArtifactId(String groupId, String artifactId) {
        List<DriverInfo> list = this.lambdaQuery()
                .eq(DriverInfo::getGroupId, groupId)
                .eq(DriverInfo::getArtifactId, artifactId)
                .orderByDesc(DriverInfo::getDriverVersion)
                .list();
        return CollUtil.isEmpty(list) ? null : list.get(0);
    }

    public boolean refreshDriver(Long id) {
        DriverInfo driverInfo = this.getById(id);
        String driverPath = driverInfo.getDriverPath();
        if(StrUtil.isNotBlank(driverPath) && FileUtil.file(driverPath).exists()){
            return true;
        }
        return this.downloadAndUpdate(driverInfo);
    }
}
