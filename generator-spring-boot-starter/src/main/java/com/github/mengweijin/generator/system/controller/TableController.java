package com.github.mengweijin.generator.system.controller;

import com.github.mengweijin.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.generator.system.service.GeneratorService;
import com.github.mengweijin.vitality.domain.layui.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/27
 */
@RestController
@RequestMapping(TableController.PREFIX)
public class TableController {
    public static final String PREFIX = "/gen/table";

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/list")
    public LayuiTable<TableInfoDTO> list(String name) {
        List<TableInfoDTO> tableInfoList = generatorService.selectTable(name);
        return new LayuiTable<>(tableInfoList);
    }
}
