package com.github.mengweijin.quickboot.framework.util;

import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
@Slf4j
public class MavenUtils {

    public static final String POM_XML = "pom.xml";

    public static Model readPomXml() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (FileReader fileReader = new FileReader(Const.PROJECT_PATH + POM_XML)){
            return reader.read(fileReader);
        } catch (IOException | XmlPullParserException e) {
            throw new QuickBootException(e);
        }
    }
}
