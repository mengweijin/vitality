package com.github.mengweijin.mybatisplus.demo.DynamicDataSource;

import lombok.Getter;

public class DynamicDataSourceHolder {

    public static final String MASTER = "master";

    public static final String SLAVE = "slave";

    @Getter
    public static final ThreadLocal<String> dataSourceThreadLocal = new ThreadLocal<>();

}
