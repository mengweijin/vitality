package com.github.mengweijin.generator.system.controller;

import com.github.mengweijin.generator.system.service.TemplateService;
import com.github.mengweijin.vitality.domain.layui.LayuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping(TemplateController.PREFIX)
public class TemplateController {

    public static final String PREFIX = "/gen/template";

    @Autowired
    private TemplateService templateService;

    @GetMapping("/tree")
    @ResponseBody
    public List<LayuiTree> tree() {
        return templateService.tree();
    }



}
