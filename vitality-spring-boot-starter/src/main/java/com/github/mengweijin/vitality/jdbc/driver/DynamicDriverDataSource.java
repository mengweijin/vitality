package com.github.mengweijin.vitality.jdbc.driver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.db.dialect.DialectFactory;
import com.github.mengweijin.vitality.exception.BusinessException;
import lombok.Getter;
import javax.sql.DataSource;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

/**
 * 动态加载数据库驱动包的 DataSource
 * @author mengweijin
 */
@Getter
public class DynamicDriverDataSource implements DataSource {

    private final JarClassLoader classLoader;

    private final String url;

    private final String username;

    private final String password;

    public DynamicDriverDataSource(String jarFilePath, String url, String username, String password) {
        this(FileUtil.file(jarFilePath), url, username, password);
    }

    public DynamicDriverDataSource(File jarFile, String url, String username, String password) {
        Assert.notNull(jarFile);
        if(!FileUtil.exist(jarFile)) {
            throw new BusinessException("File not exist at path " + jarFile.getAbsolutePath());
        }
        this.classLoader = JarClassLoader.loadJar(jarFile);
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
        DriverShim driverShim = null;
        Connection connection;
        try {
            driverShim = this.registerDriverByUrl(this.url);
            connection = DriverManager.getConnection(this.url, username, password);
        } finally {
            this.deregisterDriver(driverShim);
        }
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

    public DriverShim registerDriverByUrl(String jdbcUrl) {
        return this.registerDriver(DialectFactory.identifyDriver(jdbcUrl, this.classLoader));
    }

    /**
     *
     * @param driverClass For Example: com.mysql.cj.jdbc.Driver
     * @return DriverShim
     */
    public DriverShim registerDriver(String driverClass) {
        try {
            Driver driver = (Driver) Class.forName(driverClass, true, this.classLoader).newInstance();
            DriverShim driverShim = new DriverShim(driver);
            DriverManager.registerDriver(driverShim);
            return driverShim;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deregisterDriver(DriverShim driverShim) {
        try {
            if (driverShim != null) {
                DriverManager.deregisterDriver(driverShim);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deregisterAllDriverShim() {
        try {
            Enumeration<Driver> enumeration = DriverManager.getDrivers();
            while (enumeration.hasMoreElements()) {
                Object element = enumeration.nextElement();
                if (element.getClass() == DriverShim.class) {
                    DriverManager.deregisterDriver((DriverShim) element);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> listRegisteredDrivers() {
        List<String> list = new ArrayList<>();

        Enumeration<Driver> enumeration = DriverManager.getDrivers();
        String driverName;
        while (enumeration.hasMoreElements()) {
            Driver element = enumeration.nextElement();
            if (element.getClass() == DriverShim.class) {
                driverName = ((DriverShim) element).getDriver().getClass().getName();
            } else {
                driverName = element.getClass().getName();
            }
            list.add(driverName);
        }

        return list;
    }
}
