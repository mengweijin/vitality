package com.github.mengweijin.vitality.generator.controller;

import com.github.mengweijin.vitality.generator.domain.bo.GeneratorArgsBO;
import com.github.mengweijin.vitality.generator.domain.vo.TableInfoVO;
import com.github.mengweijin.vitality.generator.domain.vo.TemplateVO;
import com.github.mengweijin.vitality.generator.service.GeneratorService;
import com.github.mengweijin.vitality.generator.service.TemplateService;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.lang.tuple.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private TemplateService templateService;

    private GeneratorService generatorService;

    @GetMapping("/template/tree")
    public List<TemplateVO> loadTemplateTree() {
        return templateService.buildTemplateTree();
    }

    @GetMapping("/config/default")
    public GeneratorArgsBO getDefaultConfig() {
        return new GeneratorArgsBO();
    }

    @GetMapping("/table")
    public List<TableInfoVO> tableList() {
        return generatorService.getAllTableInfoVOList();
    }

    @PostMapping("/run")
    public Pair<String, String> execute(@RequestBody GeneratorArgsBO bo) {
        return generatorService.generate(bo);
    }

}
