package com.github.mengweijin.woodenman.generator.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.mengweijin.quickboot.domain.P;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.woodenman.generator.domain.JarInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Slf4j
public final class MavenJarUtils {

    /**
     * For Example: https://search.maven.org/solrsearch/select?q=g:%22com.github.mengweijin%22+AND+a:%22flyway-extend%22&start=0&rows=20
     */
    private static JarInfo searchLatestVersion(String groupId, String artifactId) {
        String url = "https://search.maven.org/solrsearch/select?q=g:%22";
        url += groupId + "%22+AND+a:%22" + artifactId + "%22&start=0&rows=20";
        try {
            String result = HttpUtil.get(url, CharsetUtil.CHARSET_UTF_8);
            JsonNode jsonNode = P.objectMapper().readTree(result);
            JsonNode docsNode = jsonNode.get("response").get("docs");
            if(docsNode.isArray() && !docsNode.isEmpty()) {
                Iterator<JsonNode> elements = docsNode.elements();
                JsonNode firstNode = elements.next();
                JsonNode latestVersion = firstNode.get("latestVersion");
                return new JarInfo(groupId, artifactId, latestVersion.asText());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void downloadJar(String groupId, String artifactId) {
        JarInfo jarInfo = searchLatestVersion(groupId, artifactId);
        if(jarInfo != null) {
            String fileUrl = getDownloadUrl(groupId, artifactId, jarInfo.getVersion());
            long size = HttpUtil.downloadFile(fileUrl, FileUtil.file(jarInfo.getSavePath()));
        }
    }

    /**
     * For Example: https://repo1.maven.org/maven2/com/github/mengweijin/quickboot-layui/1.1.0/quickboot-layui-1.1.0.jar
     */
    private static String getDownloadUrl(String groupId, String artifactId, String version) {
        String url = "https://repo1.maven.org/maven2/";
        String groupPath = StrUtil.replace(groupId, Const.DOT, Const.SLASH);
        return url + groupPath + Const.SLASH + artifactId + Const.SLASH + version + Const.SLASH + artifactId + Const.DASH + version + ".jar";
    }
}
