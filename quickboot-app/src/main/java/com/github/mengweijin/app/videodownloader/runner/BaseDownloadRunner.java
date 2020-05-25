package com.github.mengweijin.app.videodownloader.runner;

import com.github.mengweijin.app.videodownloader.entity.Task;
import com.github.mengweijin.quickboot.framework.constant.Const;
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
