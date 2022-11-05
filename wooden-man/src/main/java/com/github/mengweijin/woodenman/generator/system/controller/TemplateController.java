package com.github.mengweijin.woodenman.generator.system.controller;

import com.github.mengweijin.layui.model.LayuiTree;
import com.github.mengweijin.quickboot.mvc.BaseController;
import com.github.mengweijin.woodenman.generator.system.entity.Template;
import com.github.mengweijin.woodenman.generator.system.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping(TemplateController.PREFIX)
public class TemplateController extends BaseController {

    public static final String PREFIX = "/generator/template";

    @Autowired
    private TemplateService templateService;

    @GetMapping("/index")
    public String index() {
        this.setAttribute("treeData", templateService.tree());
        return PREFIX + "/index";
    }

    @GetMapping("/add")
    public String add() {
        return PREFIX + "/edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id) {
        this.setAttribute("domain", templateService.getById(id));
        return PREFIX + "/edit";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id) {
        this.setAttribute("domain", templateService.getById(id));
        return PREFIX + "/detail";
    }

    @PostMapping
    @ResponseBody
    public void add(Template template) {
        templateService.save(template);
    }

    @PutMapping
    @ResponseBody
    public void edit(Template template) {
        templateService.updateById(template);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        templateService.removeById(id);
    }

    @GetMapping("/tree")
    @ResponseBody
    public List<LayuiTree> tree() {
        return templateService.tree();
    }

}
