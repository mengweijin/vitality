package com.github.mengweijin.quickboot.framework.web.upload;

import com.github.mengweijin.quickboot.framework.web.upload.FileInfo;
import com.github.mengweijin.quickboot.framework.web.upload.UploadUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传 controller 暂时更适用于小文件上传（如：图片），因为使用了 MD5 来检查文件是否已经上传过了，如果上传大文件太耗费性能
 * 大文件以后可以考虑重新弄个接口，支持任务式的异步上传。
 * @author Meng Wei Jin
 **/
@Validated
public interface WebUploadController {

    /**
     * 文件上传。忽略文件是否已经上传的检查。即，一律上传，不管是否曾经上传过。
     * 如果要检查文件是否已经上传，使用函数式接口方法：
     * UploadUtils.upload(request, md5 -> { return null; });
     * 在箭头函数中，需要自定义你的检查逻辑。
     *
     * @param request request
     */
    @PostMapping("/upload")
    default void upload(HttpServletRequest request) {
        List<FileInfo> fileList = UploadUtils.upload(request);
        saveFileInfo(fileList);
    }

    /**
     * 上传文件后的钩子方法。比如：
     * 可以在数据库生成一条文件记录来保存当前文件所在的位置等信息。
     * 可以在这个方法里面再调用一个异步方法来保存文件到第三方文件服务器中。
     * @param fileInfoList List<FileInfo>
     */
    default void saveFileInfo(List<FileInfo> fileInfoList) {

    }
}
