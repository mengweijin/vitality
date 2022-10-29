package com.github.mengweijin.app.generator.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.app.cache.CacheConst;
import com.github.mengweijin.app.generator.entity.DatasourceInfo;
import com.github.mengweijin.app.generator.mapper.DatasourceMapper;
import com.github.mengweijin.quickboot.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/8/14
 */
@Service
public class DatasourceService extends ServiceImpl<DatasourceMapper, DatasourceInfo> {

    @Autowired
    private DatasourceMapper datasourceMapper;

    @Cacheable(value = CacheConst.NAME_DEFAULT, key = CacheConst.KEY_EXPRESSION_CLASS_METHOD_PARAM)
    public DbType[] getDbTypes() {
        return DbType.values();
    }

    public void cloneById(Long id) {
        DatasourceInfo datasourceInfo = this.getById(id);
        DatasourceInfo clonedDatasource = new DatasourceInfo();
        BeanUtils.copyPropertiesIgnoreBaseEntityProperties(datasourceInfo, clonedDatasource);
        this.save(clonedDatasource);
    }
}
