package com.github.mengweijin.woodenman.generator.system.service;

import com.github.mengweijin.woodenman.generator.freemarker.FreemarkerTemplateEngine;
import com.github.mengweijin.woodenman.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.woodenman.generator.system.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class TemplateGenService {

    @Autowired
    private DatasourceTableService datasourceTableService;

    @Autowired
    private TemplateService templateService;

    public String execute(Long datasourceId, String tableName, Long templateId) {
        TableInfoDTO tableInfoDTO = datasourceTableService.selectTableInfo(datasourceId, tableName);
        Template template = templateService.getById(templateId);
        return FreemarkerTemplateEngine.process(template.getName(), template.getContent(), tableInfoDTO);
    }
}
