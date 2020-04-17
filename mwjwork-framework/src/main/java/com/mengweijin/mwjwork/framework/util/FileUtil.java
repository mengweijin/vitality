package com.mengweijin.mwjwork.framework.util;

import com.mengweijin.mwjwork.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sun.net.www.protocol.file.FileURLConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Meng Wei Jin
 */
@Slf4j
public class FileUtil extends FileUtils {

    /**
     * 从jar包中的classpath下拷贝文件或文件夹到targetDir目录
     * 获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
     * @param folderPath 文件夹路径，如类路径下：files
     * @param targetDir  如：传入PROJECT_PATH = System.getProperty("user.dir")会在此目录下创建folerPath名称的目录
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void copyJarDirectoryToDirectory(String folderPath, String targetDir) throws IOException, URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(folderPath);
        if(url == null){
            throw new IllegalArgumentException("this argument is error! argument=" + folderPath);
        }
        URLConnection urlConnection = url.openConnection();

        if (urlConnection instanceof FileURLConnection) {
            // 在IDE里运行会得到FileURLConnection，使用普通方式复制文件夹即可
            FileUtils.copyDirectoryToDirectory(new File(url.toURI()), FileUtils.getFile(targetDir));
        } else if (urlConnection instanceof JarURLConnection) {
            // 项目打成jar时运行会得到JarURLConnection
            copyJarDirectoryToDirectory((JarURLConnection) urlConnection, targetDir);
        }
    }

    /**
     * 从Jar复制文件到文件夹
     * @param jarURLConnection
     * @param targetDir
     * @throws IOException
     */
    private static void copyJarDirectoryToDirectory(JarURLConnection jarURLConnection, String targetDir) throws IOException {
        JarFile jarFile = null;
        String jarEntryName;
        InputStream inputStream = null;
        File fileTargetDir;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                jarEntryName = enumeration.nextElement().getName();
                if (jarEntryName.startsWith(jarURLConnection.getEntryName())
                        && !jarEntryName.endsWith(Const.SLASH)) {
                    inputStream = classLoader.getResource(jarEntryName).openConnection().getInputStream();
                    fileTargetDir = FileUtils.getFile(targetDir + File.separator + jarEntryName);
                    FileUtils.copyInputStreamToFile(inputStream, fileTargetDir);
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

    /**
     * 使用类Files, Path, Paths 复制文件到java临时目录
     * @param srcFile
     * @return 临时路径
     * @throws IOException
     */
    public static String copyTmp(File srcFile) throws IOException {
        try(InputStream in = new FileInputStream(srcFile)) {
            Path outDirPath = Paths.get(Const.JAVA_TMP_PATH + File.separator + IdUtils.timestampId());
            if(!Files.exists(outDirPath)){
                Files.createDirectories(outDirPath);
            }
            Path outFilePath = Files.createFile(Paths.get(outDirPath.toAbsolutePath().toString() + File.separator + srcFile.getName()));
            Files.copy(in, outFilePath, StandardCopyOption.REPLACE_EXISTING);
            return outFilePath.toAbsolutePath().toString();
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new IOException(e);
        }
    }


    /**
     * windows下文件名中不能含有：\ / : * ? " < > | 英文的这些字符 ，这里使用"."、"'"进行替换。
     * \/:?| 用.替换
     * "<> 用'替换
     */
    public static String replaceSpecialCharactersInFileName(String dirPath) {
        if(dirPath == null){
            return null;
        }
        dirPath = dirPath.replaceAll("[/\\\\:*?|]", ".");
        dirPath = dirPath.replaceAll("[\"<>]", "'");
        return dirPath;
    }

}
