package com.github.mengweijin.generator.system.controller;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.generator.system.service.GeneratorService;
import com.github.mengweijin.generator.core.vo.GeneratorArgs;
import com.github.mengweijin.layui.model.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    /**
     * key: generated file name
     * value: generated file content
     * @param args GeneratorArgs
     * @return Map
     */
    @GetMapping("/render")
    public String runGeneratorByTableName(GeneratorArgs args, ModelMap map) {
        map.put("args", args);
        return "generator/render";
    }

    @GetMapping("/tableInfoList")
    @ResponseBody
    public LayuiTable getTableInfoList(@Nullable String tableName) {
        List<TableInfo> list = generatorService.selectTableInfoListByTableName(tableName);
        return LayuiTable.data(list, list.size());
    }
    @GetMapping("/tableInfoListCacheEvict")
    @ResponseBody
    public void tableInfoListCacheEvict() {
        generatorService.tableInfoListCacheEvict();
    }

}