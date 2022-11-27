package com.github.mengweijin.generator.system.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.generator.system.dto.TableFieldDTO;
import com.github.mengweijin.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.vitality.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/16
 */
@Service
public class GeneratorService extends AutoGenerator {

    public GeneratorService(@Autowired DataSource dataSource) {
        super(new DataSourceConfig.Builder(dataSource).build());
    }

    @Override
    public ConfigBuilder getConfig() {
        return new ConfigBuilder(this.getPackageInfo(), this.getDataSource(), this.getStrategy(), this.getTemplate(), this.getGlobalConfig(), this.injection);
    }

    public List<TableInfoDTO> selectTable(@Nullable String tableName) {
        List<TableInfoDTO> list = this.getTableList();
        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream()
                    .filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName))
                    .collect(Collectors.toList());
        }
        list = list.stream().sorted(Comparator.comparing(TableInfoDTO::getName)).collect(Collectors.toList());
        return list;
    }

    public List<TableInfoDTO> getTableList() {
        ConfigBuilder config = this.getConfig();
        List<TableInfo> tableInfoList = config.getTableInfoList();
        return tableInfoList.stream().map(table -> {
            TableInfoDTO dto = new TableInfoDTO();
            dto.setName(table.getName());
            dto.setHavePrimaryKey(table.isHavePrimaryKey());
            dto.setFieldNames(table.getFieldNames());
            dto.setComment(table.getComment());
            dto.setFields(BeanUtils.copyList(table.getFields(), TableFieldDTO.class));

            dto.getFields().forEach(field -> {
                field.setPropertyType(field.getColumnType().getType());
                field.setPropertyTypePackage(field.getColumnType().getPkg());
            });
            return dto;
        }).collect(Collectors.toList());
    }
}
