package com.mengweijin.video.downloader.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.mengweijin.mwjwork.common.util.UrlUtils;
import com.mengweijin.video.downloader.exception.M3U8Exception;
import com.mengweijin.video.downloader.runner.BaseDownloadRunner;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 暂时只支持一层地址
 *
 * @author mengweijin
 */
@Slf4j
public class M3U8Utils {

    /**
     * download ts files
     * @param content m3u8 content
     * @return video format
     */
    public static String downloadTS(String content, long taskId) {
        log.debug("M3U8 content: {}", content);
        String[] rows = content.split("\\n");
        // 判断是否是m3u8文本
        if(!"#EXTM3U".equalsIgnoreCase(rows[0])){
            throw new M3U8Exception("Not a valid m3u8 text!");
        }
        String tsPath = BaseDownloadRunner.OUTPUT_PATH + taskId + File.separatorChar;
        FileUtil.mkdir(tsPath);
        FileUtil.clean(tsPath);
        int index = 1;
        String tsVideoSuffix = null;
        for (String row: rows) {
            if(UrlUtils.isUrl(row)) {
                tsVideoSuffix = row.substring(row.lastIndexOf("."));
                HttpUtil.downloadFile(row, tsPath + index + tsVideoSuffix);
                index++;
                log.debug("Downloading ts file from " + row);
            }
        }
        return tsVideoSuffix;
    }

    public static File mergeTS(String fileName, String tsVideoSuffix, long taskId) {
        String tsPath = BaseDownloadRunner.OUTPUT_PATH + taskId + File.separatorChar;
        List<File> fileList =
                FileUtil.loopFiles(tsPath, pathname -> StrUtil.endWith(pathname.getName(), tsVideoSuffix))
                        .stream().sorted(Comparator.comparingInt(o -> NumberUtil.parseInt(o.getName())))
                        .collect(Collectors.toList());

        if(CollectionUtil.isNotEmpty(fileList)){
            String firstVideoName = fileList.get(0).getName();
            String videoFormat = firstVideoName.substring(firstVideoName.lastIndexOf("."));
            File file = FileUtil.file(tsPath + fileName + videoFormat);
            if(file.exists()){
                FileUtil.del(file);
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                for(File tsFile: fileList) {
                    FileUtil.writeToStream(tsFile, out);
                    FileUtil.del(tsFile);
                }
                return file;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new M3U8Exception(e);
            }
        } else {
            throw new M3U8Exception("No valid video file was found under directory {}" + BaseDownloadRunner.OUTPUT_PATH);
        }
    }
}
