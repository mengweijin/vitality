package com.github.mengweijin.app.generator.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.app.cache.CacheConst;
import com.github.mengweijin.app.generator.DefaultGenerator;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DatasourceTableService {

    @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS + "+':TableInfoList'", unless = "#result?.size() == 0")
    public List<TableInfo> getTableInfoList(DefaultGenerator generator) {
        ConfigBuilder config = generator.getConfig();
        return config.getTableInfoList();
    }

    @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS + "+':TableInfoList'")
    public void tableInfoListCacheEvictAll() {}

    public List<TableInfo> selectTableInfoList(DefaultGenerator generator, @Nullable String tableName) {
        DatasourceTableService datasourceTableService = (DatasourceTableService) AopContext.currentProxy();
        List<TableInfo> list = datasourceTableService.getTableInfoList(generator);

        if(StrUtil.isNotBlank(tableName)) {
            list = list.stream().filter(table -> StrUtil.containsIgnoreCase(table.getName(), tableName)).collect(Collectors.toList());
        }

        list = list.stream().sorted(Comparator.comparing(TableInfo::getName)).collect(Collectors.toList());
        return list;
    }




}
