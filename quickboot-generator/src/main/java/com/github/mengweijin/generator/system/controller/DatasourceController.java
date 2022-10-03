package com.github.mengweijin.generator.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.generator.system.entity.GenDatasource;
import com.github.mengweijin.generator.system.service.DatasourceService;
import com.github.mengweijin.quickboot.mvc.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping("/datasource")
public class DatasourceController extends BaseController {

    @Autowired
    private DatasourceService datasourceService;

    @GetMapping
    public String index() {
        return "system/datasource/list";
    }

    @GetMapping("/add")
    public String toAddPage() {
        return "system/datasource/edit";
    }

    @GetMapping("/edit/{id}")
    public String toEditPage(@PathVariable("id") Long id) {
        this.setAttribute("genDatasource", datasourceService.getById(id));
        return "system/datasource/edit";
    }

    @PostMapping("/add")
    @ResponseBody
    public void add(GenDatasource ds) {
        datasourceService.save(ds);
    }

    @PutMapping("/edit")
    @ResponseBody
    public void edit(GenDatasource ds) {
        datasourceService.updateById(ds);
    }

    @PostMapping("/clone/{id}")
    @ResponseBody
    public void clone(@PathVariable("id") Long id) {
        datasourceService.cloneById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        datasourceService.removeById(id);
    }

    @GetMapping("/page")
    @ResponseBody
    public Page<GenDatasource> page(Page<GenDatasource> page, GenDatasource genDatasource) {
        LambdaQueryWrapper<GenDatasource> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(genDatasource.getUsername()), GenDatasource::getUsername, genDatasource.getUsername());
        wrapper.eq(genDatasource.getDbType() != null, GenDatasource::getDbType, genDatasource.getDbType());

        return datasourceService.page(page, wrapper);
    }

}
