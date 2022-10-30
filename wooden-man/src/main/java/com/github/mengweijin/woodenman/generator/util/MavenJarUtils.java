package com.github.mengweijin.woodenman.generator.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.mengweijin.quickboot.domain.P;
import com.github.mengweijin.quickboot.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.quickboot.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Iterator;

/**
 * @author mengweijin
 * @date 2022/10/29
 */
@Slf4j
public final class MavenJarUtils {

    public static final String MODULE_NAME = "drivers";

    /**
     * For Example: https://search.maven.org/solrsearch/select?q=g:%22com.github.mengweijin%22+AND+a:%22flyway-extend%22&start=0&rows=20
     */
    public static String searchLatestVersion(String groupId, String artifactId) {
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
                return latestVersion.asText();
            }
        } catch (Exception e) {
            log.error("Search Jar URL is: " + url);
            String message = "No version was found by groupId=" + groupId + " and artifactId=" + artifactId;
            message += "; Please check your input.";
            throw new QuickBootClientException(message);
        }
        return null;
    }

    public static String downloadJar(String groupId, String artifactId) {
        return downloadJar(groupId, artifactId, null);
    }

    public static String downloadJar(String groupId, String artifactId, String version) {
        if(StrUtil.isBlank(version)) {
            version = MavenJarUtils.searchLatestVersion(groupId, artifactId);
        }
        String fileUrl = getDownloadUrl(groupId, artifactId, version);
        File file = FileUtil.file(UploadUtils.buildUploadPath(getFileName(artifactId, version), MODULE_NAME));
        try {
            long size = HttpUtil.downloadFile(fileUrl, file);
        } catch (HttpException e) {
            log.error("Download Jar URL is: " + fileUrl);
            String message = "Download jar failed. Please double check your groupId and artifactId is correct and you network is available.";
            message += "Or you can specify a version if you are not type in.";
            throw new QuickBootClientException(message, e);
        }

        return file.getAbsolutePath();
    }

    /**
     * For Example: https://repo1.maven.org/maven2/com/github/mengweijin/quickboot-layui/1.1.0/quickboot-layui-1.1.0.jar
     */
    private static String getDownloadUrl(String groupId, String artifactId, String version) {
        String url = "https://repo1.maven.org/maven2/";
        String groupPath = StrUtil.replace(groupId, Const.DOT, Const.SLASH);
        return url + groupPath + Const.SLASH + artifactId + Const.SLASH + version + Const.SLASH + getFileName(artifactId, version);
    }

    private static String getFileName(String artifactId, String version) {
        return  artifactId + Const.DASH + version + ".jar";
    }
}
