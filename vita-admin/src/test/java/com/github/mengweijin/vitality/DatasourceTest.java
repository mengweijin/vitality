package com.github.mengweijin.vitality;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author mengweijin
 */
public class DatasourceTest {

    @Test
    void getDatabaseProductName() {
        String driver = "org.h2.Driver";
        String url = "jdbc:h2:file:./h2/vitality;AUTO_SERVER=TRUE;IGNORECASE=TRUE";
        String user = "sa";
        String pass = null;

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, pass);
            DatabaseMetaData metaData = connection.getMetaData();
            Assertions.assertEquals("H2", metaData.getDatabaseProductName());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
