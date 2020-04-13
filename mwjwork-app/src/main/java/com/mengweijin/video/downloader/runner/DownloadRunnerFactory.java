package com.mengweijin.video.downloader.runner;

import com.mengweijin.mwjwork.common.exception.ClientException;
import com.mengweijin.video.downloader.runner.enums.EWebSite;
import com.mengweijin.video.downloader.runner.impl.BoosjDownloadRunner;
import com.mengweijin.video.downloader.runner.impl.GcwdpDownloadRunner;
import com.mengweijin.video.downloader.web.entity.Task;

/**
 * @author mengweijin
 */
public class DownloadRunnerFactory {

    public static BaseDownloadRunner getDownLoadRunner(Task task) {
        String url = task.getUrl();
        BaseDownloadRunner baseDownloadRunner;
        if(url.contains(EWebSite.GCWDJ.getWebSite())) {
            baseDownloadRunner = new GcwdpDownloadRunner(task);
        } else if(url.contains(EWebSite.BOOSJ.getWebSite())) {
            baseDownloadRunner = new BoosjDownloadRunner(task);
        } else {
            throw new ClientException("Not support current video url!");
        }
        return baseDownloadRunner;
    }
}
