package com.github.mengweijin.quickboot.jdbc.driver;

import lombok.Getter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author mengweijin
 */
@Getter
public class DynamicDriverDataSource implements DataSource {

    private final DynamicDriver dynamicDriver;

    private final String url;

    private final String username;

    private final String password;

    public DynamicDriverDataSource(DynamicDriver dynamicDriver, String url, String username, String password) {
        this.dynamicDriver = dynamicDriver;
        this.url = url;
        this.username = username;
        this.password = password;

    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getConnection(this.username, this.password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        dynamicDriver.registerDriverByUrl(this.url);
        Connection connection = DriverManager.getConnection(this.url, username, password);
        dynamicDriver.deregisterDriver();
        return connection;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("Can't support unwrap method!");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("Can't support isWrapperFor method!");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("DataSource can't support getParentLogger method!");
    }
}
