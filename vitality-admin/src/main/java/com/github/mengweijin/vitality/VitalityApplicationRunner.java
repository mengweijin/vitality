package com.github.mengweijin.vitality;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author mengweijin
 * @since 2023/6/6
 */
@Slf4j
@Component
@AllArgsConstructor
public class VitalityApplicationRunner implements ApplicationRunner {

    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.logDatabaseProductName();
    }

    private void logDatabaseProductName() throws SQLException {
        Connection connection = SqlSessionUtils.getSqlSession(sqlSessionFactory).getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        log.debug("Product name of the database is: {}", metaData.getDatabaseProductName());
    }
}
