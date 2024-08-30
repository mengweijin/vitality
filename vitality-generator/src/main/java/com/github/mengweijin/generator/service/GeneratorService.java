package com.github.mengweijin.generator.service;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.generator.engine.FreemarkerTemplateEngine;
import com.github.mengweijin.generator.dto.GenerateConfigDTO;
import com.github.mengweijin.generator.dto.TableFieldDTO;
import com.github.mengweijin.generator.dto.TableInfoDTO;
import com.github.mengweijin.generator.dto.TemplateDTO;
import com.github.mengweijin.generator.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.date.DatePattern;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/16
 */
@Slf4j
@Service
public class GeneratorService extends AutoGenerator {

    @Autowired
    private TemplateService templateService;

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

    public TableInfoDTO selectFirstTable(String tableName) {
        List<TableInfoDTO> tableList = this.selectTable(tableName);
        return CollUtil.isEmpty(tableList)? null : tableList.get(0);
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
            dto.setFields(Utils.copyList(table.getFields(), TableFieldDTO.class));

            dto.getFields().forEach(field -> {
                field.setPropertyType(field.getColumnType().getType());
                field.setPropertyTypePackage(field.getColumnType().getPkg());
            });
            return dto;
        }).collect(Collectors.toList());
    }

    public GenerateConfigDTO getDefaultConfig(String tableName) {
        GenerateConfigDTO config = new GenerateConfigDTO();
        config.setIgnoredColumns("ID, CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME");
        config.setEntityName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
        config.setPackagePath(Utils.getSpringBootApplicationClassPackage());
        config.setAuthor(SystemUtil.get("user.name", false));
        config.setDate(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN));

        TableInfoDTO tableInfoDTO = this.selectFirstTable(tableName);
        if(tableInfoDTO != null) {
            config.initTableInfo(tableInfoDTO);
        }

        return config;
    }

    public String generate(String tableName, String templateId, GenerateConfigDTO config) {
        TableInfoDTO tableInfoDTO = this.selectFirstTable(tableName);
        if(tableInfoDTO == null) {
            return null;
        }

        this.processIgnoredColumns(tableInfoDTO, config.getIgnoredColumns());
        config.initTableInfo(tableInfoDTO);
        log.debug(Utils.writeValueAsString(config));

        TemplateDTO templateDTO = templateService.findTemplateById(templateId);
        return FreemarkerTemplateEngine.process(templateDTO.getName(), templateDTO.getContent(), config);
    }

    private void processIgnoredColumns(TableInfoDTO tableInfoDTO, String ignoredColumns) {
        List<String> ignoredColumnList = new ArrayList<>();
        if(StrUtil.isNotBlank(ignoredColumns)) {
            ignoredColumns = StrUtil.replaceChars(ignoredColumns, new char[]{',', '，', '、', ';'}, ",").toUpperCase();
            ignoredColumnList = Arrays.stream(ignoredColumns.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        for (TableFieldDTO field: tableInfoDTO.getFields()) {
            if(ignoredColumnList.contains(field.getColumnName().toUpperCase())) {
                field.setEntityIgnored(true);
            }
        }
    }

    public void generateAndWriteToFile(String tableName, String templateId, GenerateConfigDTO config, String basePath) {
        String content = this.generate(tableName, templateId, config);
        TemplateDTO templateDTO = templateService.findTemplateById(templateId);
        String targetPath = basePath + StrUtil.replace(config.getPackagePath(), ".", "/") + File.separator;

        String[] split = templateDTO.getName().split("\\.");
        targetPath += StrUtil.replace(split[0], "{EntityName}", config.getEntityName());
        targetPath += "." + split[1];

        File file = FileUtil.file(targetPath);
        FileUtil.mkParentDirs(file);
        FileUtil.writeUtf8String(content, file);
    }
}
