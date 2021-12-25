package com.github.mengweijin.quickboot.framework.util;

import lombok.SneakyThrows;
import org.apache.maven.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
class MavenUtilsTest {

    @Test
    void readPom() {
        final Model model = MavenUtils.readPom();
        Assertions.assertEquals("quickboot-framework-spring-boot-starter", model.getArtifactId());
    }

    @Test
    @SneakyThrows
    void findPom() {
        final File pomXml = MavenUtils.findPom();
        Assertions.assertNotNull(pomXml);
        Assertions.assertEquals(MavenUtils.POM_XML, pomXml.getName());
    }
}