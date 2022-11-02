package com.github.mengweijin.woodenman.generator.system.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.cache.CacheConst;
import com.github.mengweijin.quickboot.util.BeanUtils;
import com.github.mengweijin.woodenman.generator.system.dto.DatasourceInfoDTO;
import com.github.mengweijin.woodenman.generator.system.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.system.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.maven.MavenCoordinateHelper;
import com.github.mengweijin.woodenman.generator.maven.MavenCoordinate;
import com.github.mengweijin.woodenman.generator.system.mapper.DatasourceMapper;
import com.github.mengweijin.woodenman.generator.maven.MavenJarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void cloneById(Long id) {
        DatasourceInfo datasourceInfo = this.getById(id);
        DatasourceInfo clonedDatasource = new DatasourceInfo();
        BeanUtils.copyPropertiesIgnoreBaseEntityProperties(datasourceInfo, clonedDatasource);
        this.save(clonedDatasource);
    }

    public boolean updateDriverIdById(Long id, Long driverId) {
        return this.lambdaUpdate().set(DatasourceInfo::getDriverId, driverId).eq(DatasourceInfo::getId, id).update();
    }

    public boolean refreshDriver(DatasourceInfo datasourceInfo) {
        MavenCoordinate mavenCoordinate = MavenCoordinateHelper.getMavenCoordinate(datasourceInfo.getDbType());
        if(mavenCoordinate == null) {
            return false;
        }

        Optional<DriverInfo> optional = driverService.getOneAvailableDriverFromSystem(mavenCoordinate.getGroupId(), mavenCoordinate.getArtifactId());
        if(optional.isPresent()){
            return this.updateDriverIdById(datasourceInfo.getId(), optional.get().getId());
        }

        String path = MavenJarUtils.downloadJar(mavenCoordinate.getGroupId(), mavenCoordinate.getArtifactId());
        if(StrUtil.isNotBlank(path)) {
            DriverInfo driverInfo = new DriverInfo();
            driverInfo.setGroupId(mavenCoordinate.getGroupId());
            driverInfo.setArtifactId(mavenCoordinate.getArtifactId());
            driverInfo.setDriverPath(path);
            driverService.save(driverInfo);

            return this.updateDriverIdById(datasourceInfo.getId(), driverInfo.getId());
        }
        return false;
    }
}
