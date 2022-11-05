package com.github.mengweijin.woodenman.generator.system.controller;

import com.github.mengweijin.layui.model.LayuiTable;
import com.github.mengweijin.quickboot.mvc.BaseController;
import com.github.mengweijin.woodenman.generator.DefaultGenerator;
import com.github.mengweijin.woodenman.generator.system.dto.TableInfoDTO;
import com.github.mengweijin.woodenman.generator.system.service.DatasourceTableService;
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
@RequestMapping(DatasourceTableController.PREFIX)
public class DatasourceTableController extends BaseController {

    public static final String PREFIX = "generator/datasource/table";

    @Autowired
    private DatasourceTableService datasourceTableService;

    @GetMapping("/{datasourceId}/index")
    public String index(@PathVariable("datasourceId") Long datasourceId) {
        this.setAttribute("datasourceId", Long.toString(datasourceId));
        return PREFIX + "/index";
    }

    @GetMapping("/{datasourceId}/list")
    @ResponseBody
    public LayuiTable<TableInfoDTO> list(@PathVariable("datasourceId") Long datasourceId, String tableName) {
        DefaultGenerator generator = datasourceTableService.createDefaultGenerator(datasourceId);
        List<TableInfoDTO> tableInfoList = datasourceTableService.selectTableInfoList(generator, tableName);
        return new LayuiTable<>(tableInfoList);
    }

    @PostMapping("/tableInfoListCacheEvictAll")
    @ResponseBody
    public void tableInfoListCacheEvictAll() {
        datasourceTableService.tableInfoListCacheEvictAll();
    }

}
