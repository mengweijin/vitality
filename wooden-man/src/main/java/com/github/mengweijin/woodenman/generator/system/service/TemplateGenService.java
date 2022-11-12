package com.github.mengweijin.woodenman.generator.system.service;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.domain.P;
import com.github.mengweijin.woodenman.generator.freemarker.FreemarkerTemplateEngine;
import com.github.mengweijin.woodenman.generator.system.dto.GenerateConfig;
import com.github.mengweijin.woodenman.generator.system.dto.TableFieldDTO;
import com.github.mengweijin.woodenman.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.woodenman.generator.system.entity.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Slf4j
@Service
public class TemplateGenService {

    @Autowired
    private DatasourceTableService datasourceTableService;

    @Autowired
    private TemplateService templateService;

    public String execute(Long datasourceId, String tableName, Long templateId, GenerateConfig config) {
        String ignoredColumns = config.getIgnoredColumns();
        List<String> ignoredColumnList = new ArrayList<>();
        if(StrUtil.isNotBlank(ignoredColumns)) {
            ignoredColumns = StrUtil.replaceChars(ignoredColumns, new char[]{',', '，', '、', ';'}, ",").toUpperCase();
            ignoredColumnList = StrUtil.splitTrim(ignoredColumns, ',');
        }

        TableInfoDTO tableInfoDTO = datasourceTableService.selectTableInfo(datasourceId, tableName);
        for (TableFieldDTO field: tableInfoDTO.getFields()) {
            if(ignoredColumnList.contains(field.getColumnName().toUpperCase())) {
                field.setEntityIgnored(true);
            }
        }
        config.build(tableInfoDTO);

        log.debug(P.writeValueAsString(config));
        Template template = templateService.getById(templateId);
        return FreemarkerTemplateEngine.process(template.getName(), template.getContent(), config);
    }
}
