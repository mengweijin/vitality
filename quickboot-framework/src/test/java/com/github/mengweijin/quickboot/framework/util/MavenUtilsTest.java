package com.github.mengweijin.quickboot.framework.util;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
class MavenUtilsTest {

    @Test
    void readPom() {
        final Model model = MavenUtils.readPom("com.github.mengweijin", "quickboot-framework");
        Assertions.assertEquals("quickboot-framework", model.getArtifactId());
    }

}