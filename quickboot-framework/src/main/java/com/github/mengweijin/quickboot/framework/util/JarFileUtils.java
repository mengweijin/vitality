package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Meng Wei Jin
 */
@Slf4j
public class JarFileUtils {

    /**
     * if (new OsInfo().isWindows()) {
     *     // 从应用程序jar里面的classpath下的files目录，拷贝 7z等文件到应用程序jar包同级目录
     *     FileUtils.copyJarDirectoryToDirectory("files", Const.PROJECT_PATH);
     *     log.debug("Copy classpath:files to " + Const.PROJECT_PATH + "files finished!");
     * }
     *
     * 从jar包中的classpath下拷贝文件或文件夹到targetDir目录
     * 获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
     * @param folderPath 文件夹路径，如类路径下：files
     * @param targetDir  如：传入PROJECT_PATH = System.getProperty("user.dir")会在此目录下创建folerPath名称的目录
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void copyJarDirectoryToDirectory(String folderPath, String targetDir) throws IOException, URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(folderPath);
        if(url == null) {
            throw new IllegalArgumentException("this argument is error! argument=" + folderPath);
        }

        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof JarURLConnection) {
            // 项目打成jar时运行会得到JarURLConnection
            copyJarDirectoryToDirectory((JarURLConnection) urlConnection, targetDir);
        } else {
            // 在IDE里运行使用普通方式复制文件夹即可
            FileUtil.copy(FileUtil.file(url.toURI()), FileUtil.file(targetDir), true);
        }
    }

    /**
     * 从Jar复制文件到文件夹
     * @param connection
     * @param targetDir
     * @throws IOException
     */
    private static void copyJarDirectoryToDirectory(JarURLConnection connection, String targetDir) throws IOException {
        JarFile jarFile = null;
        String jarEntryName;
        InputStream inputStream = null;
        File fileTargetDir;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            jarFile = connection.getJarFile();
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                jarEntryName = enumeration.nextElement().getName();
                if (jarEntryName.startsWith(connection.getEntryName())
                        && !jarEntryName.endsWith(Const.SLASH)) {
                    inputStream = classLoader.getResource(jarEntryName).openConnection().getInputStream();
                    fileTargetDir = FileUtil.file(targetDir + File.separator + jarEntryName);
                    FileUtil.writeFromStream(inputStream, fileTargetDir);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IOException(e);
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(jarFile != null){
                jarFile.close();
            }
        }
    }
}
