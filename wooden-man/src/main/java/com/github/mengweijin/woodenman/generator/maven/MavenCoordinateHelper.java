package com.github.mengweijin.woodenman.generator.maven;

import com.baomidou.mybatisplus.annotation.DbType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
public class MavenCoordinateHelper {

    private static final Map<DbType, MavenCoordinate> MAP = new HashMap<>();

    static {
        MAP.put(DbType.MYSQL, new MavenCoordinate("mysql", "mysql-connector-java"));
        MAP.put(DbType.MARIADB, new MavenCoordinate("org.mariadb.jdbc", "org.mariadb.jdbc"));
        MAP.put(DbType.ORACLE, new MavenCoordinate("com.oracle.ojdbc", "ojdbc8"));
        MAP.put(DbType.ORACLE_12C, new MavenCoordinate("com.oracle.ojdbc", "ojdbc8"));
        MAP.put(DbType.DB2, new MavenCoordinate("com.ibm.db2", "jcc"));
        MAP.put(DbType.H2, new MavenCoordinate("com.h2database", "h2"));
        MAP.put(DbType.HSQL, new MavenCoordinate("org.hsqldb", "hsqldb"));
        MAP.put(DbType.SQLITE, new MavenCoordinate("org.xerial", "sqlite-jdbc"));
        MAP.put(DbType.POSTGRE_SQL, new MavenCoordinate("org.postgresql", "postgresql"));
        MAP.put(DbType.SQL_SERVER2005, new MavenCoordinate("com.microsoft.sqlserver", "mssql-jdbc"));
        MAP.put(DbType.SQL_SERVER, new MavenCoordinate("com.microsoft.sqlserver", "mssql-jdbc"));
        MAP.put(DbType.DM, new MavenCoordinate("com.dameng", "DmJdbcDriver18"));
    }

    public static MavenCoordinate getMavenCoordinate(DbType dbType) {
        return MAP.get(dbType);
    }

}
