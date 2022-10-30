package com.github.mengweijin.woodenman.generator.util;

import cn.hutool.core.lang.JarClassLoader;

import java.io.File;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.sql.DriverManager;


/**
 * @author mengweijin
 * @date 2022/10/30
 */
public class DriverUtils {

    /**
     * DriverUtil.identifyDriver(jdbcUrl)
     * DriverManager.getConnection(jdbcUrl, "root", "");
     * DriverManager.deregisterDriver(driverShim);
     */
    public static DriverShim registerDriver(File jarFile, String driverClass) {
        try(URLClassLoader classLoader = JarClassLoader.loadJarToSystemClassLoader(jarFile)){
            Thread.currentThread().setContextClassLoader(classLoader);

            Driver driver = (Driver) Class.forName(driverClass, true, classLoader).newInstance();
            DriverShim driverShim = new DriverShim(driver);
            DriverManager.registerDriver(driverShim);
            return driverShim;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deregisterDriver(DriverShim driverShim) {
        try {
            DriverManager.registerDriver(driverShim);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
