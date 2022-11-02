package com.github.mengweijin.quickboot.jdbc.driver;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
class DynamicDriverTest {

    public static final String jar5 = "D:\\repository\\mysql\\mysql-connector-java\\5.1.18\\mysql-connector-java-5.1.18.jar";
    public static final String jar8 = "D:\\repository\\mysql\\mysql-connector-java\\8.0.30\\mysql-connector-java-8.0.30.jar";

    public static final String driver5 = "com.mysql.jdbc.Driver";
    public static final String driver8 = "com.mysql.cj.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/flowable?useSSL=false";
    public static final String username = "root";
    public static final String password = "root";

    /**
     * Connection connection = DriverManager.getConnection(url, username, password);
     */
    @Test
    void registerDriverAndQueryFromDB() {
        DynamicDriver dynamicDriver = registerDriver8();

        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from ACT_ADM_DATABASECHANGELOG");
        list.forEach(System.out::println);

        dynamicDriver.deregisterDriver();
        List<String> list2 = DynamicDriver.listRegisteredDrivers();
        list2.forEach(System.out::println);
    }

    @Test
    void registerDriver5() {
        DynamicDriver dynamicDriver = new DynamicDriver(FileUtil.file(jar5));
        dynamicDriver.registerDriver(driver5);
        DriverShim driverShim = dynamicDriver.getDriverShim();
        System.out.println(driverShim.getMajorVersion() + "-------" + driverShim.getMinorVersion());
    }

    @Test
    DynamicDriver registerDriver8() {
        DynamicDriver dynamicDriver = new DynamicDriver(FileUtil.file(jar8));
        dynamicDriver.registerDriver(driver8);
        DriverShim driverShim = dynamicDriver.getDriverShim();
        System.out.println(driverShim.getMajorVersion() + "-------" + driverShim.getMinorVersion());
        return dynamicDriver;
    }

    @Test
    void registerDriver5And8() {
        registerDriver5();
        registerDriver8();
        List<String> list = DynamicDriver.listRegisteredDrivers();
        list.forEach(System.out::println);
        System.out.println("---------------------------------");
        DynamicDriver.deregisterAllDriverShim();
        list = DynamicDriver.listRegisteredDrivers();
        list.forEach(System.out::println);
    }
}