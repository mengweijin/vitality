package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
@Slf4j
public class MavenUtils {

    public static final String POM_XML = "pom.xml";

    public static Model readPom(String groupId, String artifactId) {
        try{
            URL url = Thread.currentThread().getContextClassLoader().getResource("");
            URLConnection urlConnection = url.openConnection();

            MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
            if (urlConnection instanceof JarURLConnection) {
                return loadPomFromJar(groupId, artifactId, mavenXpp3Reader);
            } else {
                // 在IDE里运行使用普通方式文件系统读取文件夹即可
                return loadPomFromFileSystem(url, mavenXpp3Reader);
            }
        } catch (IOException | URISyntaxException e) {
            throw new QuickBootException(e);
        }
    }

    /**
     * resourcePath: "/META-INF/maven/com.github.mengweijin/quickboot-devtool/pom.xml"
     */
    public static Model loadPomFromJar(String groupId, String artifactId, MavenXpp3Reader mavenXpp3Reader) {
        String resourcePath = "/META-INF/maven/" + groupId + "/" + artifactId + "pom.xml";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try(InputStream inputStream = classLoader.getResourceAsStream(resourcePath)){
            return mavenXpp3Reader.read(inputStream);
        } catch (XmlPullParserException | IOException e) {
            throw new QuickBootException(e);
        }
    }

    /**
     * 在IDE里运行使用普通方式文件系统读取文件夹即可
     * @param url 此时 url = file:/C:/Source/code/gitee/quickboot/quickboot-framework-spring-boot-starter/target/test-classes/
     * @param mavenXpp3Reader
     * @return
     * @throws URISyntaxException
     */
    public static Model loadPomFromFileSystem(URL url, MavenXpp3Reader mavenXpp3Reader) throws URISyntaxException {
        File classPath = FileUtil.file(url.toURI());
        File projectDir = FileUtil.getParent(classPath, 2);
        File pomFile = FileUtil.file(projectDir, POM_XML);
        try(FileReader fileReader = new FileReader(pomFile)){
            return mavenXpp3Reader.read(fileReader);
        } catch (XmlPullParserException | IOException e) {
            throw new QuickBootException(e);
        }
    }

}
