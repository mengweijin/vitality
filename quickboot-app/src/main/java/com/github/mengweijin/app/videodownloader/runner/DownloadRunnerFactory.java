package com.github.mengweijin.app.videodownloader.runner;

import com.github.mengweijin.app.videodownloader.entity.Task;
import com.github.mengweijin.app.videodownloader.runner.enums.EWebSite;
import com.github.mengweijin.app.videodownloader.runner.impl.BoosjDownloadRunner;
import com.github.mengweijin.app.videodownloader.runner.impl.GcwdpDownloadRunner;
import com.github.mengweijin.quickboot.framework.exception.ClientException;

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
