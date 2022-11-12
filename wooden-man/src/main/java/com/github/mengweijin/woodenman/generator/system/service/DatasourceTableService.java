package com.github.mengweijin.woodenman.generator.system.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.quickboot.cache.CacheConst;
import com.github.mengweijin.quickboot.jdbc.driver.DynamicDriver;
import com.github.mengweijin.quickboot.jdbc.driver.DynamicDriverDataSource;
import com.github.mengweijin.quickboot.util.BeanUtils;
import com.github.mengweijin.woodenman.generator.DefaultGenerator;
import com.github.mengweijin.woodenman.generator.system.dto.TableFieldDTO;
import com.github.mengweijin.woodenman.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.woodenman.generator.system.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.system.entity.DriverInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Slf4j
@Service
public class DatasourceTableService {
    @Autowired
    private DatasourceService datasourceService;
    @Autowired
    private DriverService driverService;

    public TableInfoDTO selectTableInfo(Long datasourceId, String tableName) {
        List<TableInfoDTO> dtoList = this.selectTableInfoList(datasourceId, tableName);
        return dtoList.get(0);
    }

    public List<TableInfoDTO> selectTableInfoList(Long datasourceId, @Nullable String tableName) {
        DatasourceTableService datasourceTableService = (DatasourceTableService) AopContext.currentProxy();
        List<TableInfoDTO> list = datasourceTableService.getTableInfoList(datasourceId);
        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream()
                    .filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName))
                    .collect(Collectors.toList());
        }
        list = list.stream().sorted(Comparator.comparing(TableInfoDTO::getName)).collect(Collectors.toList());
        return list;
    }

    @CacheEvict(
            value = CacheConst.NAME_DEFAULT,
            key = CacheConst.KEY_CLASS + " + ':getTableInfoList:datasourceId:' + #datasourceId"
    )
    public void tableInfoListCacheEvict(Long datasourceId) {
        log.debug("Cache Evict for datasourceId " + datasourceId);
    }

    @Cacheable(
            value = CacheConst.NAME_DEFAULT,
            key = CacheConst.KEY_CLASS + " + ':getTableInfoList:datasourceId:' + #datasourceId",
            unless = CacheConst.UNLESS_LIST
    )
    public List<TableInfoDTO> getTableInfoList(Long datasourceId) {
        DefaultGenerator generator = this.createDefaultGenerator(datasourceId);
        ConfigBuilder config = generator.getConfig();
        List<TableInfo> tableInfoList = config.getTableInfoList();
        return tableInfoList.stream().map(table -> {
            TableInfoDTO dto = new TableInfoDTO();
            dto.setName(table.getName());
            dto.setHavePrimaryKey(table.isHavePrimaryKey());
            dto.setFieldNames(table.getFieldNames());
            dto.setComment(table.getComment());
            dto.setFields(BeanUtils.copyList(table.getFields(), TableFieldDTO.class));

            dto.getFields().forEach(field -> {
                field.setPropertyName(field.getColumnType().getType());
                field.setPropertyTypePackage(field.getColumnType().getPkg());
            });
            return dto;
        }).collect(Collectors.toList());
    }

    private DefaultGenerator createDefaultGenerator(Long datasourceId) {
        DatasourceInfo ds = datasourceService.getById(datasourceId);
        DriverInfo driverInfo = driverService.getById(ds.getDriverId());
        File jarFile = FileUtil.file(driverInfo.getDriverPath());
        DynamicDriver dynamicDriver = new DynamicDriver(jarFile);
        DynamicDriverDataSource dataSource =
                new DynamicDriverDataSource(dynamicDriver, ds.getUrl(), ds.getUsername(), ds.getPassword());
        return new DefaultGenerator(dataSource);
    }



}
