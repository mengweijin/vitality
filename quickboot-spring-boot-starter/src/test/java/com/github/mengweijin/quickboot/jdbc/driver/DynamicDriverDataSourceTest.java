package com.github.mengweijin.quickboot.jdbc.driver;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
class DynamicDriverDataSourceTest {
    public static final String jar5 = "D:\\repository\\mysql\\mysql-connector-java\\5.1.18\\mysql-connector-java-5.1.18.jar";
    public static final String jar8 = "D:\\repository\\mysql\\mysql-connector-java\\8.0.30\\mysql-connector-java-8.0.30.jar";

    public static final String driver5 = "com.mysql.jdbc.Driver";
    public static final String driver8 = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/flowable?useSSL=false";
    public static final String username = "root";
    public static final String password = "root";

    @Test
    void getConnection(){
        DynamicDriver dynamicDriver = new DynamicDriver(jar8);

        DynamicDriverDataSource dataSource = new DynamicDriverDataSource(dynamicDriver, url, username, password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from ACT_ADM_DATABASECHANGELOG");
        list.forEach(System.out::println);

        List<String> list2 = DynamicDriver.listRegisteredDrivers();
        list2.forEach(System.out::println);
    }
}