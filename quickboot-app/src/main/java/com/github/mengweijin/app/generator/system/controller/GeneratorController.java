package com.github.mengweijin.app.generator.system.controller;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.app.generator.system.service.GeneratorService;
import com.github.mengweijin.layui.model.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
@RequestMapping("/gen")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @GetMapping
    public String index() {
        return "generator/index";
    }

    @GetMapping("/tableInfoList")
    @ResponseBody
    public LayuiTable<TableInfo> getTableInfoList(@Nullable String tableName) {
        List<TableInfo> list = generatorService.selectTableInfoListByTableName(tableName);
        return new LayuiTable<>(list);
    }
    @GetMapping("/tableInfoListCacheEvict")
    @ResponseBody
    public void tableInfoListCacheEvict() {
        generatorService.tableInfoListCacheEvict();
    }

}
