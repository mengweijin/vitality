package com.github.mengweijin.vitality;

import com.github.mengweijin.framework.constant.Const;
import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dromara.hutool.core.io.file.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2023/12/17
 */
class ChangePomVersion {
    private static final String PARENT_POM_XML = Const.PROJECT_DIR + "pom.xml";
    private static final String[] MODULE_POM_XML = {
            Const.PROJECT_DIR + "vitality-admin/pom.xml",
            Const.PROJECT_DIR + "vitality-framework/pom.xml",
            Const.PROJECT_DIR + "vitality-generator/pom.xml",
            Const.PROJECT_DIR + "vitality-layui/pom.xml",
    };

    private static final String NEW_VERSION = "1.4.1-SNAPSHOT";

    @SneakyThrows
    public static void main(String[] args) {
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

        changeParent(reader);
        changeModule(reader);
    }

    @SneakyThrows
    private static void changeParent(SAXReader reader) {
        File file = FileUtil.file(PARENT_POM_XML);

        Document document = reader.read(file);
        Element rootElement = document.getRootElement();
        Optional<Element> rootOptional = Optional.of(rootElement);
        Element versionElement = rootOptional.map(e -> e.element("version")).orElse(null);
        versionElement.setText(ChangePomVersion.NEW_VERSION); // 更新 Version

        Element vitalityVersionElement = rootOptional
                .map(e -> e.element("properties"))
                .map(e -> e.element("vitality.version")).orElse(null);
        vitalityVersionElement.setText(ChangePomVersion.NEW_VERSION);

        write(document, file);
    }

    @SneakyThrows
    private static void changeModule(SAXReader reader) {
        for (String pomPath : MODULE_POM_XML) {
            File file = FileUtil.file(pomPath);
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();
            Optional<Element> rootOptional = Optional.of(rootElement);

            Element element = rootOptional
                    .map(e -> e.element("parent"))
                    .map(e -> e.element("version")).orElse(null);
            element.setText(ChangePomVersion.NEW_VERSION);

            write(document, file);
        }
    }

    private static void write(Document document, File outFile) {
        System.out.println("Changing " + outFile.getAbsolutePath());
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