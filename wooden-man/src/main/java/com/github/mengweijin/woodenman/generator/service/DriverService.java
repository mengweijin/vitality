package com.github.mengweijin.woodenman.generator.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.mapper.DriverMapper;
import com.github.mengweijin.woodenman.generator.util.MavenJarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DriverService extends ServiceImpl<DriverMapper, DriverInfo> {

    @Autowired
    private DriverMapper driverMapper;

    public boolean downloadAndUpdate(Long id) {
        DriverInfo driverInfo = this.getById(id);
        if(StrUtil.isBlank(driverInfo.getDriverVersion())) {
            driverInfo.setDriverVersion(MavenJarUtils.searchLatestVersion(driverInfo.getGroupId(), driverInfo.getArtifactId()));
        }
        String path = MavenJarUtils.downloadJar(driverInfo.getGroupId(), driverInfo.getArtifactId(), driverInfo.getDriverVersion());

        return this.lambdaUpdate()
                .set(DriverInfo::getDriverPath, path)
                .eq(DriverInfo::getId, id)
                .update();
    }

}
