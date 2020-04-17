package com.mengweijin.app.videodownloader.runner.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.app.videodownloader.runner.BaseDownloadRunner;
import com.mengweijin.app.videodownloader.runner.model.BoosjVideoInfo;
import com.mengweijin.app.videodownloader.util.FfmpegUtils;
import com.mengweijin.app.videodownloader.util.M3U8Utils;
import com.mengweijin.mwjwork.framework.constant.Const;
import com.mengweijin.mwjwork.framework.exception.ServerException;
import com.mengweijin.mwjwork.framework.util.http.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 播视网(http://www.boosj.com/)
 *
 * @author mengweijin
 */
@Slf4j
public class BoosjDownloadRunner extends BaseDownloadRunner {

    private static final String M3U8_JSON_URL = "http://gslb.boosj.com/ips.json";

    public BoosjDownloadRunner(Task task) {
        this.setTask(task);
    }

    @Override
    public File execute() {
        BoosjVideoInfo videoInfo = this.getVideoInfo(this.getTask().getUrl());
        String m3u8Content = getM3u8Content(videoInfo.getVid());
        String tsVideoSuffix = M3U8Utils.downloadTS(m3u8Content, this.getTask().getId());
        File tsFile = M3U8Utils.mergeTS(String.valueOf(this.getTask().getId()), tsVideoSuffix, this.getTask().getId());
        String mp4FilePath = tsFile.getParentFile().getAbsolutePath() + File.separatorChar + tsFile.getName() + ".mp4";
        FfmpegUtils.convert(tsFile.getAbsolutePath(), mp4FilePath);
        FileUtil.del(tsFile);

        String videoName = com.mengweijin.mwjwork.framework.util.FileUtil.replaceSpecialCharactersInFileName(videoInfo.getVideoName());
        return FileUtil.rename(FileUtil.file(mp4FilePath), videoName + Const.UNDERSCORE + this.getTask().getId(), true, true);
    }

    private BoosjVideoInfo getVideoInfo(String indexUrl) {
        BoosjVideoInfo boosjVideoInfo = null;
        try {
            Document document = JsoupUtils.getDocument(indexUrl);
            Elements scriptElement = document.select("head > script");

            JSONObject playVideoInfo;
            for(Element element: scriptElement){
                String text = element.html();
                if(text.contains("var PlayVideoInfo")){
                    playVideoInfo = JSONObject.parseObject(text.substring(text.indexOf("{")));
                    log.debug("playVideoInfo: \n{}", JSONObject.toJSONString(playVideoInfo, SerializerFeature.PrettyFormat));
                    boosjVideoInfo = new BoosjVideoInfo();
                    boosjVideoInfo.setVid(playVideoInfo.getString("vid"));
                    boosjVideoInfo.setVideoName(playVideoInfo.getString("videoName"));
                    break;
                }
            }
        } catch (NoSuchAlgorithmException | IOException | KeyManagementException e) {
            log.error(e.getMessage(), e);
            throw new ServerException(e);
        }

        log.debug("Get videoInfo end. videoInfo={}", boosjVideoInfo);
        return boosjVideoInfo;
    }

    private String getM3u8Content(String vid) {
        String ipsJson = HttpUtil.get(M3U8_JSON_URL);
        JSONObject ipsJsonObject = JSONObject.parseObject(ipsJson);

        // data.gslb + "?_id=" + vid     8494541
        String gslbUrl = ipsJsonObject.getString("gslb") + "?_id=" + vid + "&" + HttpUtil.toParams(ipsJsonObject);
        String gslbResponse = HttpUtil.get(gslbUrl);

        JSONObject gslbJsonObject = JSONObject.parseObject(gslbResponse);
        String m3u8Url = gslbJsonObject.getString("url") + "?" + gslbJsonObject.getString("t");

        String m3u8String = HttpUtil.get(m3u8Url);
        log.debug("Get m3u8 file content: \n" + m3u8String);

        return m3u8String;
    }
}
