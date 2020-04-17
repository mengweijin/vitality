package com.mengweijin.app.videodownloader.runner;

import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.mwjwork.framework.constant.Const;
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
