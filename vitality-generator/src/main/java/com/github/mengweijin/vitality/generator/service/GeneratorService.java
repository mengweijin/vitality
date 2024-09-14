package com.github.mengweijin.vitality.generator.service;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.vitality.framework.util.BeanUtils;
import com.github.mengweijin.vitality.generator.domain.bo.GeneratorArgsBO;
import com.github.mengweijin.vitality.generator.domain.dto.GeneratorArgs;
import com.github.mengweijin.vitality.generator.domain.vo.TableFieldVO;
import com.github.mengweijin.vitality.generator.domain.vo.TableInfoVO;
import com.github.mengweijin.vitality.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vitality.generator.engine.VelocityTemplateEngine;
import com.github.mengweijin.vitality.generator.util.GeneratorUtils;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.lang.tuple.Pair;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.PropertyPlaceholderHelper;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/16
 */
@Slf4j
@Service
@AllArgsConstructor
public class GeneratorService {

    private DataSource dataSource;

    private TemplateService templateService;

    private VelocityTemplateEngine velocityTemplateEngine;

    public Pair<String, String> generate(GeneratorArgsBO args) {
        TemplateVO template = templateService.findById(args.getTemplateId());
        String fileName = this.parseFileName(template, args);
        TableInfo tableInfo = this.findTableInfoByName(args.getTableName());
        return velocityTemplateEngine.writeString(fileName, template.getContent(), new GeneratorArgs(args), tableInfo);
    }

    private String parseFileName(TemplateVO template, GeneratorArgsBO args) {
        String entityName = GeneratorUtils.resolveEntityName(args.getTableName(), args.getTablePrefix().toArray(new String[0]));
        Properties props = new Properties();
        props.setProperty("EntityName", StrUtil.toStringOrNull(entityName));

        PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");
        String fileName = placeholderHelper.replacePlaceholders(template.getName(), props);
        return StrUtil.subBefore(fileName, ".", true);
    }

    /**
     * Using front-end filtering
     *
     * @param tableName tableName
     * @return List<TableInfoVO>
     */
    @Deprecated
    public List<TableInfoVO> selectTableInfo(@Nullable String tableName) {
        List<TableInfoVO> list = this.getAllTableInfoVOList();
        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream().filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName)).toList();
        }
        return list.stream().sorted(Comparator.comparing(TableInfoVO::getName)).collect(Collectors.toList());
    }

    public List<TableInfoVO> getAllTableInfoVOList() {
        List<TableInfo> tableInfoList = this.selectTableInfoList(null);
        return tableInfoList.stream().map(table -> {
            TableInfoVO dto = new TableInfoVO();
            dto.setName(table.getName());
            dto.setHavePrimaryKey(table.isHavePrimaryKey());
            dto.setFieldNames(table.getFieldNames());
            dto.setComment(table.getComment());
            dto.setFields(BeanUtils.copyList(table.getFields(), TableFieldVO.class));
            dto.getFields().forEach(field -> {
                field.setPropertyType(field.getColumnType().getType());
                field.setPropertyTypePackage(field.getColumnType().getPkg());
            });
            return dto;
        }).collect(Collectors.toList());
    }

    private List<TableInfo> selectTableInfoList(@Nullable String tableName) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(dataSource).build();

        StrategyConfig.Builder strategyConfigBuilder = GeneratorBuilder.strategyConfigBuilder();
        if (StrUtil.isNotBlank(tableName)) {
            strategyConfigBuilder.addInclude(tableName);
        }

        // ConfigBuilder 类不能扩展，会报错。
        ConfigBuilder configBuilder = new ConfigBuilder(null, dataSourceConfig, strategyConfigBuilder.build(), null, null, null);
        return configBuilder.getTableInfoList();
    }

    private TableInfo findTableInfoByName(String tableName) {
        List<TableInfo> list = this.selectTableInfoList(tableName);
        return list.stream().findFirst().orElse(null);
    }

}
