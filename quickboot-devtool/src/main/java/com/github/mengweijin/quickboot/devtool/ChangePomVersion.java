package com.github.mengweijin.quickboot.devtool;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

/**
 * @author mengweijin
 */
public class ChangePomVersion {

    private static final String POM_XML = "pom.xml";

    private static final String OLD_VERSION = "1.0.27";

    private static final String NEW_VERSION = "1.0.28-SNAPSHOT";

    @SneakyThrows
    public static void main(String[] args) {

        execute(OLD_VERSION, NEW_VERSION);
    }

    public static void execute(String oldVersion, String newVersion) throws DocumentException {
        List<File> fileList = FileUtil.loopFiles(Const.PROJECT_PATH, file -> POM_XML.equals(file.getName()));

        SAXReader reader = new SAXReader();
        for (File file : fileList) {
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();
            Optional<Element> rootOptional = Optional.of(rootElement);

            // change quickboot-parent version in pom.xml
            setQuickBootParentVersion(rootElement);

            // change quickboot-parent version in pom.xml properties
            Element quickBootVersionElement = rootOptional
                    .map(e -> e.element("properties"))
                    .map(e -> e.element("quickboot.version")).orElse(null);

            if (quickBootVersionElement != null && oldVersion.equals(quickBootVersionElement.getStringValue())) {
                quickBootVersionElement.setText(newVersion);
            }

            Element parentElement = rootOptional
                    .map(e -> e.element("parent")).orElse(null);
            // change quickboot children project version in pom.xml parent element
            setQuickBootParentVersion(parentElement);

            write(document, file);
        }
    }

    private static void setQuickBootParentVersion(Element element) {
        if (element == null) {
            return;
        }
        Optional<Element> optional = Optional.ofNullable(element);
        String groupId = optional
                .map(e -> e.element("groupId"))
                .map(Element::getStringValue).orElse(null);
        String artifactId = optional
                .map(e -> e.element("artifactId"))
                .map(Element::getStringValue).orElse(null);

        Element versionElement = optional
                .map(e -> e.element("version")).orElse(null);
        String version = Optional.ofNullable(versionElement)
                .map(Element::getStringValue).orElse(null);

        if ("com.github.mengweijin".equals(groupId)
                && "quickboot-parent".equals(artifactId)
                && OLD_VERSION.equals(version)) {
            // 更新 Version
            versionElement.setText(NEW_VERSION);
        }
    }

    private static void write(Document document, File outFile) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 缩进4个空格
        format.setIndentSize(4);
        try (OutputStream out = new FileOutputStream(outFile)) {
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
