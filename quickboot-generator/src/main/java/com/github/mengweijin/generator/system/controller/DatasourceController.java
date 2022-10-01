package com.github.mengweijin.generator.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.generator.system.entity.GenDatasource;
import com.github.mengweijin.generator.system.service.DatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping("/datasource")
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    @GetMapping
    public String index() {
        return "system/datasource/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<GenDatasource> list(Page<GenDatasource> page, GenDatasource genDatasource) {
        return datasourceService.page(page, new QueryWrapper<>(genDatasource));
    }
}
