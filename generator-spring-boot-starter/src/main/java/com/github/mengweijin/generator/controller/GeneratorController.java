package com.github.mengweijin.generator.controller;

import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.github.mengweijin.generator.DefaultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private DefaultGenerator defaultGenerator;

    @GetMapping("/run")
    public void runGenerator(DataSource dataSource){
        FastAutoGenerator.create(new DataSourceConfig.Builder(dataSource))
                .globalConfig(builder -> { builder
                        .author(SystemUtil.get("user.name", false))
                        .outputDir("D://")
                        .disableOpenDir()
                        .enableSwagger()
                        .enableSpringdoc()
                        .dateType(DateType.TIME_PACK)
                        .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent("com.github.mengweijin") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "C://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_simple") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    @Cacheable("TABLE_INFO_LIST")
    @GetMapping("/tableInfoList")
    public List<TableInfo> getTableInfo(){
        ConfigBuilder config = defaultGenerator.getConfig();
        return config.getTableInfoList();
    }
}
