package com.mengweijin.video.downloader.util;

import cn.hutool.system.OsInfo;
import com.mengweijin.mwjwork.common.constant.Const;
import com.mengweijin.mwjwork.common.exception.ServerException;
import com.mengweijin.mwjwork.common.util.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * download ffmpeg.exe from https://ffmpeg.zeranoe.com/builds/
 *
 * @author mengweijin
 */
@Slf4j
public class FfmpegUtils {

    /**
     * 格式转换
     * linux: ffmpeg -i abc.ts abc.mp4
     * windows: C:/files/ffmpeg.exe -i abc.ts abc.mp4
     * @param source 如：abc.ts
     * @param destination 如：abc.mp4
     */
    public static void convert(String source, String destination) {
        String command;
        OsInfo osInfo = new OsInfo();
        if(osInfo.isWindows()){
            command = String.format("%s -y -i \"%s\" \"%s\"", Const.PROJECT_PATH + "files/ffmpeg.exe", source, destination);
        } else if(osInfo.isLinux()){
            command = String.format("ffmpeg -y -i \"%s\" \"%s\"", source, destination);
        } else {
            throw new ServerException("The current operating system is not supported.");
        }
        log.debug("command: " + command);
        ProcessUtils.executeCommand(command);
    }
}
