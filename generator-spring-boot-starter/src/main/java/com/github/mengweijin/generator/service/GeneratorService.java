package com.github.mengweijin.generator.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.generator.DefaultGenerator;
import com.github.mengweijin.generator.constant.TableInfoCacheKey;
import com.github.mengweijin.generator.vo.GeneratorArgs;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class GeneratorService {

    @Autowired
    private DefaultGenerator defaultGenerator;

    @Cacheable(TableInfoCacheKey.TABLE_INFO_LIST)
    public List<TableInfo> getTableInfoList() {
        ConfigBuilder config = defaultGenerator.getConfig();
        return config.getTableInfoList();
    }

    @CacheEvict(TableInfoCacheKey.TABLE_INFO_LIST)
    public void tableInfoListCacheEvict() {}

    public List<TableInfo> selectTableInfoListByTableName(String tableName) {
        GeneratorService generatorService = (GeneratorService) AopContext.currentProxy();
        List<TableInfo> list = generatorService.getTableInfoList();

        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream().filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName)).collect(Collectors.toList());
        }

        list = list.stream().sorted(Comparator.comparing(TableInfo::getName)).collect(Collectors.toList());
        return list;
    }

    public Map<String, String> runGeneratorByTableName(GeneratorArgs args) {
        Map<String, String> map = new LinkedHashMap<>();
        TableInfo tableInfo = this.selectTableInfoListByTableName(args.getTableName()).get(0);

        return map;
    }

}
