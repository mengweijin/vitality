package com.github.mengweijin.generator.system.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.generator.cache.CacheKey;
import com.github.mengweijin.generator.system.entity.GenDatasource;
import com.github.mengweijin.generator.system.mapper.DatasourceMapper;
import com.github.mengweijin.quickboot.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DatasourceService extends ServiceImpl<DatasourceMapper, GenDatasource> {

    @Autowired
    private DatasourceMapper datasourceMapper;

    @Cacheable(CacheKey.DB_TYPE)
    public DbType[] getDbTypes() {
        return DbType.values();
    }

    public void cloneById(Long id) {
        GenDatasource genDatasource = this.getById(id);
        GenDatasource clonedDatasource = new GenDatasource();
        BeanUtils.copyPropertiesIgnoreBaseEntityProperties(genDatasource, clonedDatasource);
        this.save(clonedDatasource);
    }
}
