package com.github.mengweijin.generator.controller;

import com.github.mengweijin.generator.dto.GenerateArgsDTO;
import com.github.mengweijin.generator.dto.GenerateConfigDTO;
import com.github.mengweijin.generator.service.GeneratorService;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/config/default/{tableName}")
    public GenerateConfigDTO getDefaultConfig(@PathVariable String tableName) {
        return generatorService.getDefaultConfig(tableName);
    }

    @PostMapping("/execute/{tableName}")
    public String execute(
                          @PathVariable("tableName") String tableName,
                          @RequestBody GenerateArgsDTO dto) {
        if(CollUtil.isEmpty(dto.getTemplateIdList())) {
            return null;
        }
        return generatorService.generate(tableName, dto.getTemplateIdList().get(0), dto.getConfig());
    }

    @PostMapping("/executeBatch/{tableName}")
    public String executeBatchToFile(
                                        @PathVariable("tableName") String tableName,
                                        @RequestBody GenerateArgsDTO dto) {
        String basePath = System.getProperty("user.dir")  + File.separatorChar + "target/generator/";
        FileUtil.del(FileUtil.file(basePath));
        List<String> templateIdList = dto.getTemplateIdList();
        if(CollUtil.isNotEmpty(templateIdList)) {
            templateIdList.forEach(templateId -> {
                generatorService.generateAndWriteToFile(tableName, templateId, dto.getConfig(), basePath);
            });
        }
        return basePath;
    }
}
