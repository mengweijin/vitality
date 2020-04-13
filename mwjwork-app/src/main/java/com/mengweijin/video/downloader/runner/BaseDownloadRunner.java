package com.mengweijin.video.downloader.runner;

import com.mengweijin.mwjwork.common.constant.Const;
import com.mengweijin.video.downloader.web.entity.Task;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * @author mengweijin
 */
public abstract class BaseDownloadRunner {

    public static final String OUTPUT_PATH = Const.PROJECT_PATH + "video" + File.separatorChar;

    @Getter
    @Setter
    private Task task;

    /**
     * execute runner
     * @return file
     */
    public abstract File execute();
}
