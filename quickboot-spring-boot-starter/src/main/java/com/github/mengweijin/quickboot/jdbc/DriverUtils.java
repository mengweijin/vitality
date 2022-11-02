package com.github.mengweijin.quickboot.jdbc;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.dialect.DriverNamePool;
import cn.hutool.db.dialect.DriverUtil;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
public class DriverUtils extends DriverUtil implements DriverNamePool {

    public static String identifyDriver(String nameContainsProductInfo, ClassLoader classLoader) {
        if (StrUtil.isBlank(nameContainsProductInfo)) {
            return null;
        }
        // 全部转为小写，忽略大小写
        nameContainsProductInfo = StrUtil.cleanBlank(nameContainsProductInfo.toLowerCase());

        // 首先判断是否为标准的JDBC URL，截取jdbc:xxxx:中间部分
        final String name = ReUtil.getGroup1("jdbc:(.*?):", nameContainsProductInfo);
        if(StrUtil.isNotBlank(name)){
            nameContainsProductInfo = name;
        }

        String driver = null;
        if (nameContainsProductInfo.contains("mysql") || nameContainsProductInfo.contains("cobar")) {
            driver = ClassLoaderUtil.isPresent(DRIVER_MYSQL_V6, classLoader) ? DRIVER_MYSQL_V6 : DRIVER_MYSQL;
        } else if (nameContainsProductInfo.contains("oracle")) {
            driver = ClassLoaderUtil.isPresent(DRIVER_ORACLE, classLoader) ? DRIVER_ORACLE : DRIVER_ORACLE_OLD;
        } else if (nameContainsProductInfo.contains("postgresql")) {
            driver = DRIVER_POSTGRESQL;
        } else if (nameContainsProductInfo.contains("sqlite")) {
            driver = DRIVER_SQLLITE3;
        } else if (nameContainsProductInfo.contains("sqlserver") || nameContainsProductInfo.contains("microsoft")) {
            driver = DRIVER_SQLSERVER;
        } else if (nameContainsProductInfo.contains("hive2")) {
            driver = DRIVER_HIVE2;
        } else if (nameContainsProductInfo.contains("hive")) {
            driver = DRIVER_HIVE;
        } else if (nameContainsProductInfo.contains("h2")) {
            driver = DRIVER_H2;
        } else if (nameContainsProductInfo.contains("derby")) {
            // 嵌入式Derby数据库
            driver = DRIVER_DERBY;
        } else if (nameContainsProductInfo.contains("hsqldb")) {
            // HSQLDB
            driver = DRIVER_HSQLDB;
        } else if (nameContainsProductInfo.contains("dm")) {
            // 达梦7
            driver = DRIVER_DM7;
        } else if (nameContainsProductInfo.contains("kingbase8")) {
            // 人大金仓8
            driver = DRIVER_KINGBASE8;
        } else if (nameContainsProductInfo.contains("ignite")) {
            // Ignite thin
            driver = DRIVER_IGNITE_THIN;
        } else if (nameContainsProductInfo.contains("clickhouse")) {
            // ClickHouse
            driver = DRIVER_CLICK_HOUSE;
        } else if (nameContainsProductInfo.contains("highgo")) {
            // 瀚高
            driver = DRIVER_HIGHGO;
        } else if (nameContainsProductInfo.contains("db2")) {
            // DB2
            driver = DRIVER_DB2;
        } else if (nameContainsProductInfo.contains("xugu")) {
            // 虚谷
            driver = DRIVER_XUGU;
        } else if (nameContainsProductInfo.contains("phoenix")) {
            // Apache Phoenix
            driver = DRIVER_PHOENIX;
        } else if (nameContainsProductInfo.contains("zenith")) {
            // 华为高斯
            driver = DRIVER_GAUSS;
        } else if (nameContainsProductInfo.contains("gbase")) {
            // 华为高斯
            driver = DRIVER_GBASE;
        } else if (nameContainsProductInfo.contains("oscar")) {
            // 神州数据库
            driver = DRIVER_OSCAR;
        } else if (nameContainsProductInfo.contains("sybase")) {
            // 神州数据库
            driver = DRIVER_SYBASE;
        } else if (nameContainsProductInfo.contains("xugu")) {
            // 虚谷数据库
            driver = DRIVER_XUGO;
        }

        return driver;
    }
}
