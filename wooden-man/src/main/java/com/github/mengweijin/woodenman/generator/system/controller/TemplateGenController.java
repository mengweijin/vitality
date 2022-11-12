package com.github.mengweijin.woodenman.generator.system.controller;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.layui.model.LayuiTree;
import com.github.mengweijin.quickboot.mvc.BaseController;
import com.github.mengweijin.woodenman.generator.system.dto.GenerateConfig;
import com.github.mengweijin.woodenman.generator.system.service.TemplateGenService;
import com.github.mengweijin.woodenman.generator.system.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping(TemplateGenController.PREFIX)
public class TemplateGenController extends BaseController {

    public static final String PREFIX = "/generator/template/gen";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateGenService templateGenService;

    @GetMapping("/{datasourceId}/{tableName}/index")
    public String index(@PathVariable("datasourceId") Long datasourceId, @PathVariable("tableName") String tableName) {
        List<LayuiTree> tree = templateService.tree();
        this.setAttribute("datasourceId", datasourceId);
        this.setAttribute("tableName", tableName);
        this.setAttribute("entityName", StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
        this.setAttribute("templateList", tree);
        this.setAttribute("generateConfig", new GenerateConfig());
        return PREFIX + "/index";
    }

    @ResponseBody
    @PostMapping("/execute/{datasourceId}/{tableName}/{templateId}")
    public String execute(@PathVariable("datasourceId") Long datasourceId,
                          @PathVariable("tableName") String tableName,
                          @PathVariable("templateId") Long templateId,
                          GenerateConfig generateConfig) {
        return templateGenService.execute(datasourceId, tableName, templateId);
    }

}
