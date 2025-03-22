package com.github.mengweijin.vita.framework.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Meng Wei Jin
 */
@Slf4j
public class JarFileUtils {

    /**
     * 读取类路径下的文件内容
     * @param classpathDirectory 类路径下的文件夹
     * @return list
     */
    public static List<ContentInfo> loadContentInfo(String classpathDirectory) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(classpathDirectory);
            assert url != null;
            URLConnection urlConnection = url.openConnection();
            if (urlConnection instanceof JarURLConnection connection) {
                // 项目打成jar时运行会得到JarURLConnection
                return loadClasspathContentInfo(connection, classLoader);
            } else {
                // 在IDE里运行使用普通方式
                return loadFileSystemContentInfo(urlConnection);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ContentInfo> loadFileSystemContentInfo(URLConnection connection) {
        List<ContentInfo> list = new ArrayList<>();
        List<File> templateList = new ArrayList<>();
        FileUtil.walkFiles(FileUtil.file(connection.getURL()), templateList::add);

        templateList.forEach(file -> {
            ContentInfo contentInfo = new ContentInfo();
            contentInfo.setId(file.getPath());
            contentInfo.setParentId(file.getParentFile().getPath());
            contentInfo.setName(file.getName());
            if (file.isDirectory()) {
                contentInfo.setDirectory(true);
            } else {
                contentInfo.setDirectory(false);
                contentInfo.setContent(FileUtil.readUtf8String(file));
            }
            list.add(contentInfo);
        });
        return list;
    }

    private static List<ContentInfo> loadClasspathContentInfo(JarURLConnection connection, ClassLoader classLoader) throws IOException {
        List<ContentInfo> list = new ArrayList<>();

        JarFile jarFile = connection.getJarFile();
        Enumeration<JarEntry> enumeration = jarFile.entries();
        JarEntry jarEntry;
        String entryName;
        while (enumeration.hasMoreElements()) {
            jarEntry = enumeration.nextElement();
            entryName = jarEntry.getName();
            if (entryName.startsWith(connection.getEntryName())) {
                ContentInfo contentInfo = new ContentInfo();

                contentInfo.setId(entryName);
                contentInfo.setParentId(getParentPath(entryName));
                contentInfo.setName(getName(entryName));
                if (jarEntry.isDirectory()) {
                    contentInfo.setDirectory(true);
                } else {
                    contentInfo.setDirectory(false);
                    URLConnection conn = Objects.requireNonNull(classLoader.getResource(entryName)).openConnection();
                    String content = IoUtil.readUtf8(conn.getInputStream());
                    contentInfo.setContent(content);
                }
                list.add(contentInfo);
            }
        }
        return list;
    }

    private static String getParentPath(String path) {
        if (StrUtil.isBlank(path)) {
            return "";
        }
        String[] split = path.split("/");
        split[split.length - 1] = "";
        return String.join("/", split);
    }

    private static String getName(String path) {
        if (StrUtil.isBlank(path)) {
            return "";
        }
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    @Data
    public static class ContentInfo {

        private String id;

        private String parentId;

        private boolean directory;

        private String name;

        private String content;

    }
}
