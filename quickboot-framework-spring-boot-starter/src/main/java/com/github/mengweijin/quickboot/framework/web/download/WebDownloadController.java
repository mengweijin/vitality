package com.github.mengweijin.quickboot.framework.web.download;

import com.github.mengweijin.quickboot.framework.web.download.DownLoadUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 文件下载 controller
 * @author Meng Wei Jin
 **/
@Validated
public interface WebDownloadController {

    /**
     * 文件下载
     * @param fileId 文件唯一标识
     * @param request request
     * @param response response
     */
    @GetMapping("/download/{fileId}")
    default void download(@PathVariable("fileId") String fileId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        DownLoadUtils.download(getFileByFileId(fileId), request, response);
    }

    /**
     * 文件下载，断点续传
     * @param fileId 文件唯一标识
     * @param request request
     * @param response response
     */
    @GetMapping("/download/chunked/{fileId}")
    default void downloadChunked(@PathVariable("fileId") String fileId,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        DownLoadUtils.chunkDownload(getFileByFileId(fileId), request, response);
    }

    /**
     * 根据文件 id 获取文件对象。（一般不要实现的是 fileId 为服务器上的绝对全路径这样搞，会被恶意用户下载到服务器上所有的文件！）。
     * @param fileId 文件 id。比如数据库中存储的文件表中的 id。或者一个在服务器上的文件绝对路径。或者其他情况，根据应用存放文件的方案来实现具体逻辑。
     * @return File
     */
    File getFileByFileId(String fileId);
}
