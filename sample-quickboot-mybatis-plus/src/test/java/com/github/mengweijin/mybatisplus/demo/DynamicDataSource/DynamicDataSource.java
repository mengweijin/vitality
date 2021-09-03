package com.github.mengweijin.mybatisplus.demo.DynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Meng Wei Jin
 * @description 动态数据源
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceThreadLocal().get();
    }

}