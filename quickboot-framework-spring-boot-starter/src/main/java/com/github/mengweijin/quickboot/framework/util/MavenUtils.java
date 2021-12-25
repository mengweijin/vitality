package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
@Slf4j
public class MavenUtils {

    public static final String POM_XML = "pom.xml";

    public static Model readPom() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (FileReader fileReader = new FileReader(findPom())){
            return reader.read(fileReader);
        } catch (IOException | XmlPullParserException | URISyntaxException e) {
            throw new QuickBootException(e);
        }
    }

    public static File findPom() throws IOException, URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        URLConnection urlConnection = url.openConnection();
        if (urlConnection instanceof JarURLConnection) {
            // 项目打成jar时运行会得到JarURLConnection
            JarURLConnection jarURLConnection = (JarURLConnection) urlConnection;
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> enumeration = jarFile.entries();
            while (enumeration.hasMoreElements()) {
                String jarEntryName = enumeration.nextElement().getName();
                System.out.println(jarEntryName);
                // if (jarEntryName.startsWith(jarURLConnection.getEntryName())
                //         && !jarEntryName.endsWith(Const.SLASH)) {
                //     inputStream = classLoader.getResource(jarEntryName).openConnection().getInputStream();
                //     fileTargetDir = FileUtil.file(targetDir + File.separator + jarEntryName);
                //     FileUtil.writeFromStream(inputStream, fileTargetDir);
                //     if (inputStream != null) {
                //         inputStream.close();
                //     }
                // }
            }


            // quickboot-mybatis-plus-spring-boot-starter-1.0.33.jar\META-INF\maven\com.github.mengweijin\quickboot-mybatis-plus-spring-boot-starter\pom.xml



            return null;
        } else {
            // 在IDE里运行使用普通方式文件系统读取文件夹即可
            // url = file:/C:/Source/code/gitee/quickboot/quickboot-framework-spring-boot-starter/target/classes/
            File classPath = FileUtil.file(url.toURI());
            File projectDir = FileUtil.getParent(classPath, 2);
            return FileUtil.file(projectDir, POM_XML);
        }
    }
}
