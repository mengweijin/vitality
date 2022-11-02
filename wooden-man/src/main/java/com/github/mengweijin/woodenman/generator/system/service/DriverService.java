package com.github.mengweijin.woodenman.generator.system.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.mengweijin.quickboot.exception.QuickBootClientException;
import com.github.mengweijin.woodenman.generator.maven.MavenJarUtils;
import com.github.mengweijin.woodenman.generator.system.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.system.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.system.mapper.DatasourceMapper;
import com.github.mengweijin.woodenman.generator.system.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DriverService extends ServiceImpl<DriverMapper, DriverInfo> {

    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private DatasourceMapper datasourceMapper;

    public Optional<DriverInfo> getOneAvailableDriverFromSystem(String groupId, String artifactId) {
        List<DriverInfo> list = this.lambdaQuery()
                .eq(DriverInfo::getGroupId, groupId)
                .eq(DriverInfo::getArtifactId, artifactId)
                .orderByDesc(DriverInfo::getDriverVersion)
                .list();
        return list.stream().filter(info -> this.checkDriverJarFileExist(info.getDriverPath())).findFirst();
    }

    public boolean fetchDriverById(Long id) {
        String path;
        DriverInfo driverInfo = this.getById(id);
        Optional<DriverInfo> optional = this.getOneAvailableDriverFromSystem(driverInfo.getGroupId(), driverInfo.getArtifactId());
        if(optional.isPresent()) {
            path = optional.get().getDriverPath();
        } else {
            path = MavenJarUtils.downloadJar(driverInfo.getGroupId(), driverInfo.getArtifactId(), driverInfo.getDriverVersion());
        }
        return this.updateDriverPathById(id, path);
    }

    public boolean updateDriverPathById(Long id, String driverPath) {
        return this.lambdaUpdate()
                .set(DriverInfo::getDriverPath, driverPath)
                .eq(DriverInfo::getId, id)
                .update();
    }

    public boolean checkById(Long id) {
        DriverInfo driverInfo = this.getById(id);
        String driverPath = driverInfo.getDriverPath();
        return checkDriverJarFileExist(driverPath);
    }

    private boolean checkDriverJarFileExist(String driverPath) {
        return StrUtil.isNotBlank(driverPath) && FileUtil.exist(FileUtil.file(driverPath)) && driverPath.endsWith(".jar");
    }

    @Override
    public boolean removeById(Serializable id) {
        LambdaQueryWrapper<DatasourceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DatasourceInfo::getDriverId, id);
        Long count = datasourceMapper.selectCount(wrapper);
        if(count > 0) {
            throw new QuickBootClientException("The associated data exists and cannot be deleted.");
        }
        return SqlHelper.retBool(driverMapper.deleteById(id));
    }
}
