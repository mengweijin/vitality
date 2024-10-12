package com.github.mengweijin.vitality.framework;

import com.github.mengweijin.vitality.system.service.ConfigService;
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

    private ConfigService configService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String databaseProductName = this.getDatabaseProductName();
        log.info("-----> Product name of the database is: {}", databaseProductName);

        configService.list();
        log.info("-----> Load config data success.");
    }

    private String getDatabaseProductName() throws SQLException {
        Connection connection = SqlSessionUtils.getSqlSession(sqlSessionFactory).getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        return metaData.getDatabaseProductName();
    }

}
