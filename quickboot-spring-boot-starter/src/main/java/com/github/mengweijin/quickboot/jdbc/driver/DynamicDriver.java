package com.github.mengweijin.quickboot.jdbc.driver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.JarClassLoader;
import com.github.mengweijin.quickboot.exception.QuickBootException;
import com.github.mengweijin.quickboot.jdbc.DriverUtils;
import lombok.Getter;

import java.io.File;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * DriverUtil.identifyDriver(jdbcUrl)
 * DriverManager.getConnection(jdbcUrl, "root", "");
 * DriverManager.deregisterDriver(driverShim);
 *
 * @author mengweijin
 * @date 2022/10/30
 */
@Getter
public class DynamicDriver {

    private final JarClassLoader classLoader;

    private DriverShim driverShim;

    public DynamicDriver(String jarFilePath) {
        this(FileUtil.file(jarFilePath));
    }

    /**
     * @param jarFile For Example: File mysql-connector-java-8.0.30.jar
     */
    public DynamicDriver(File jarFile) {
        Assert.notNull(jarFile);
        if(!FileUtil.exist(jarFile)) {
            throw new QuickBootException("File not found at path " + jarFile.getAbsolutePath());
        }
        this.classLoader = JarClassLoader.loadJar(jarFile);
    }

    public void registerDriverByUrl(String jdbcUrl) {
        this.registerDriver(DriverUtils.identifyDriver(jdbcUrl, this.classLoader));
    }

    /**
     * String driverClass = DriverUtil.identifyDriver(jdbcUrl, DRIVER_CLASSLOADER);
     *
     * @param driverClass For Example: com.mysql.cj.jdbc.Driver
     * @return DriverShim
     */
    public void registerDriver(String driverClass) {
        try {
            Driver driver = (Driver) Class.forName(driverClass, true, this.classLoader).newInstance();
            DriverShim driverShim = new DriverShim(driver);
            DriverManager.registerDriver(driverShim);
            this.driverShim = driverShim;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deregisterDriver() {
        try {
            if (this.driverShim != null) {
                DriverManager.deregisterDriver(this.driverShim);
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
