package com.github.mengweijin.generator.system.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.generator.system.entity.Db;
import com.github.mengweijin.generator.system.mapper.DbMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class DbService extends ServiceImpl<DbMapper, Db> implements IService<Db> {

    @Autowired
    private DbMapper dbMapper;

    @Cacheable("DB_TYPE_ARRAY")
    public DbType[] getDbTypes() {
        return DbType.values();
    }
}

