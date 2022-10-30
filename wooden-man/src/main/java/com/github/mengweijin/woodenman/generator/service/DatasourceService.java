package com.github.mengweijin.woodenman.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.cache.CacheConst;
import com.github.mengweijin.quickboot.util.BeanUtils;
import com.github.mengweijin.woodenman.generator.dto.DatasourceInfoDTO;
import com.github.mengweijin.woodenman.generator.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.factory.DefaultDriverFactory;
import com.github.mengweijin.woodenman.generator.factory.MavenCoordinate;
import com.github.mengweijin.woodenman.generator.mapper.DatasourceMapper;
import com.github.mengweijin.woodenman.generator.util.MavenJarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DatasourceService extends ServiceImpl<DatasourceMapper, DatasourceInfo> {

    @Autowired
    private DatasourceMapper datasourceMapper;

    @Autowired
    private DriverService driverService;

    @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS_METHOD_PARAM)
    public DbType[] getDbTypes() {
        return DbType.values();
    }

    public IPage<DatasourceInfoDTO> selectPageVO(IPage<DatasourceInfoDTO> page, DatasourceInfoDTO dto){
        return datasourceMapper.selectPageVO(page, dto);
    }

    public void cloneById(String id) {
        DatasourceInfo datasourceInfo = this.getById(id);
        DatasourceInfo clonedDatasource = new DatasourceInfo();
        BeanUtils.copyPropertiesIgnoreBaseEntityProperties(datasourceInfo, clonedDatasource);
        this.save(clonedDatasource);
    }

    public boolean updateDriverIdById(String driverId, String id) {
        return this.lambdaUpdate().set(DatasourceInfo::getDriverId, driverId).eq(DatasourceInfo::getId, id).update();
    }

    public boolean refreshDriver(String id) {
        DatasourceInfo datasourceInfo = this.getById(id);
        if(datasourceInfo.getDriverId() != null) {
            return driverService.refreshDriver(datasourceInfo.getDriverId());
        }

        MavenCoordinate mavenCoordinate = DefaultDriverFactory.getMavenCoordinate(datasourceInfo.getDbType());
        if(mavenCoordinate == null) {
            return false;
        }

        DriverInfo driverInfo = driverService.getMaxVersionDriverInfoByGroupIdAndArtifactId(mavenCoordinate.getGroupId(), mavenCoordinate.getArtifactId());
        if(driverInfo != null) {
            this.updateDriverIdById(driverInfo.getId(), id);
            return driverService.refreshDriver(driverInfo.getId());
        } else {
            String path = MavenJarUtils.downloadJar(mavenCoordinate.getGroupId(), mavenCoordinate.getArtifactId());
            driverInfo = new DriverInfo();
            driverInfo.setGroupId(mavenCoordinate.getGroupId());
            driverInfo.setArtifactId(mavenCoordinate.getArtifactId());
            driverInfo.setDriverPath(path);
            driverService.save(driverInfo);
            return this.updateDriverIdById(driverInfo.getId(), id);
        }
    }
}
