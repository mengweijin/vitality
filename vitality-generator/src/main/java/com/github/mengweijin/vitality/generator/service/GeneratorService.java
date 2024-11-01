package com.github.mengweijin.vitality.generator.service;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.util.BeanUtils;
import com.github.mengweijin.vitality.generator.domain.bo.GeneratorArgsBO;
import com.github.mengweijin.vitality.generator.domain.dto.GeneratorArgs;
import com.github.mengweijin.vitality.generator.domain.vo.ContentVO;
import com.github.mengweijin.vitality.generator.domain.vo.TableFieldVO;
import com.github.mengweijin.vitality.generator.domain.vo.TableInfoVO;
import com.github.mengweijin.vitality.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vitality.generator.engine.VelocityTemplateEngine;
import com.github.mengweijin.vitality.generator.util.GeneratorUtils;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.compress.ZipUtil;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2022/8/16
 */
@Slf4j
@Service
@AllArgsConstructor
public class GeneratorService {

    public static final String DOWNLOAD_BASE_TEMP_DIR = Const.JAVA_TMP_DIR + "generator" + File.separator;

    private DataSource dataSource;

    private TemplateService templateService;

    private VelocityTemplateEngine velocityTemplateEngine;

    public ContentVO generate(GeneratorArgsBO args) {
        String fileName = this.parseFileName(args.getTemplateName(), args.getTableName(), args.getTablePrefix());
        TableInfo tableInfo = this.findTableInfoByName(args.getTableName());
        String content = velocityTemplateEngine.writeString(fileName, args.getTemplateContent(), new GeneratorArgs(args), tableInfo);

        ContentVO contentVO = new ContentVO();
        contentVO.setFileName(fileName);
        contentVO.setContent(content);
        return contentVO;
    }

    private String parseFileName(String fileName, String tableName, String prefix) {
        String[] tablePrefix = GeneratorUtils.parseTablePrefix(prefix);
        String entityName = GeneratorUtils.resolveEntityName(tableName, tablePrefix);
        return GeneratorUtils.replacePlaceHolders(fileName, "entityName", StrUtil.toStringOrNull(entityName));
    }

    public List<TableInfoVO> selectTableList(@Nullable String tableName) {
        List<TableInfoVO> list = this.getAllTableInfoVOList();
        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream().filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName)).toList();
        }
        return list.stream().sorted(Comparator.comparing(TableInfoVO::getName)).collect(Collectors.toList());
    }

    private List<TableInfoVO> getAllTableInfoVOList() {
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

    public File download(GeneratorArgsBO bo) {
        String currentBasePath = DOWNLOAD_BASE_TEMP_DIR + IdUtil.fastSimpleUUID();
        List<TemplateVO> templateList = templateService.getTemplateList();
        templateList.forEach(tpl -> {
            if (!tpl.isDirectory()) {
                bo.setTemplateId(tpl.getId());
                bo.setTemplateName(tpl.getName());
                bo.setTemplateContent(tpl.getContent());

                ContentVO contentVO = this.generate(bo);
                String filePath = StrUtil.subAfter(tpl.getId(), TemplateService.TEMPLATE_DIR, false);
                filePath = parseFileName(filePath, bo.getTableName(), bo.getTablePrefix());
                String fullPath = String.join(File.separator, currentBasePath, filePath);
                File file = FileUtil.file(fullPath);
                FileUtil.mkParentDirs(file);
                FileUtil.writeUtf8String(contentVO.getContent(), file);
            }
        });

        File zip = ZipUtil.zip(FileUtil.file(currentBasePath));
        FileUtil.del(currentBasePath);
        log.debug("Generated zip file path = {}", zip.getAbsolutePath());
        return zip;
    }
}
