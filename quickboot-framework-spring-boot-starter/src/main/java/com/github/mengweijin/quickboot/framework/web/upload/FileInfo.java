package com.github.mengweijin.quickboot.framework.web.upload;

import lombok.Data;

/**
 * 参考 WebUploadController
 * @author mengweijin
 * @date 2021/12/24
 */
@Data
public class FileInfo {

    private String originalName;

    private String md5;

    /**
     * 应用服务器上存放文件的位置
     */
    private String location;

    public FileInfo(String originalName, String md5, String location) {
        this.originalName = originalName;
        this.md5 = md5;
        this.location = location;
    }

}
