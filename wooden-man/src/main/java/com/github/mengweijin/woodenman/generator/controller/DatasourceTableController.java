package com.github.mengweijin.woodenman.generator.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.db.dialect.DriverUtil;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.layui.model.LayuiTable;
import com.github.mengweijin.quickboot.mvc.BaseController;
import com.github.mengweijin.woodenman.generator.DefaultGenerator;
import com.github.mengweijin.woodenman.generator.entity.DatasourceInfo;
import com.github.mengweijin.woodenman.generator.entity.DriverInfo;
import com.github.mengweijin.woodenman.generator.service.DatasourceService;
import com.github.mengweijin.woodenman.generator.service.DatasourceTableService;
import com.github.mengweijin.woodenman.generator.service.DriverService;
import com.github.mengweijin.woodenman.generator.util.DriverShim;
import com.github.mengweijin.woodenman.generator.util.DriverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
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

    @Autowired
    private DatasourceService datasourceService;
    @Autowired
    private DriverService driverService;

    @GetMapping("/{datasourceId}/index")
    public String index(@PathVariable("datasourceId") String datasourceId) {
        this.setAttribute("datasourceId", datasourceId);
        return PREFIX + "/index";
    }

    @GetMapping("/{datasourceId}/list")
    @ResponseBody
    public LayuiTable<TableInfo> list(@PathVariable("datasourceId") String datasourceId, String tableName) {
        DatasourceInfo ds = datasourceService.getById(datasourceId);
        DriverInfo driverInfo = driverService.getById(ds.getDriverId());
        File jarFile = FileUtil.file(driverInfo.getDriverPath());
        DriverShim driverShim = DriverUtils.registerDriver(jarFile, DriverUtil.identifyDriver(ds.getUrl()));
        DriverManagerDataSource dataSource = new DriverManagerDataSource(ds.getUrl(), ds.getUsername(), ds.getPassword());
        DefaultGenerator generator = new DefaultGenerator(dataSource);
        List<TableInfo> tableInfoList = datasourceTableService.selectTableInfoList(generator, tableName);
        DriverUtils.deregisterDriver(driverShim);
        return new LayuiTable<>(tableInfoList);
    }

    @PostMapping("/tableInfoListCacheEvictAll")
    @ResponseBody
    public void tableInfoListCacheEvictAll() {
        datasourceTableService.tableInfoListCacheEvictAll();
    }

}
