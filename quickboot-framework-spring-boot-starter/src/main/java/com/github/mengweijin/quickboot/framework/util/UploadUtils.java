package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.domain.FileInfo;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 文件上传。
 * 暂时更适用于小文件上传（如：图片），因为使用了 MD5 来检查文件是否已经上传过了，如果上传大文件太耗费性能
 * 大文件以后可以考虑重新弄个接口，支持任务式的异步上传。
 * @author Meng Wei Jin
 **/
@Slf4j
public class UploadUtils {

    /**
     * 上传文件存放根路径
     */
    public static final String UPLOAD_ROOT_PATH = Const.PROJECT_PATH + "upload" + File.separatorChar;

    /**
     * 未知文件类型的文件存放目录。PROJECT_PATH/upload/others/
     */
    public static final String UNKNOWN_FILE_TYPE_DIR = "others";

    /**
     * 文件上传。@PostMapping("/upload")
     * 可以传入一个 Function<String, FileInfo> Lambda 表达式来检查文件是否已经在服务器上有一份了。
     *
     * @param request request
     * @param function 函数式接口。根据 MD5 在数据库中检查是否文件已经存在。注意：MD5 操作是一件比较耗费性能的计算，尤其是文件较大时。
     * @param consumer 函数式接口。上传文件后，可以在数据库生成一条文件记录来保存当前文件所在的位置等信息。
     *                 可以在这个方法里面再调用一个异步方法来保存文件到第三方文件服务器中。
     */
    public static void upload(HttpServletRequest request, Function<String, FileInfo> function, Consumer<List<FileInfo>> consumer) {
        List<FileInfo> fileList = UploadUtils.upload(request, function);
        consumer.accept(fileList);
    }

    /**
     * 上传文件。根据文件后缀名自动分文件夹存放。
     *
     * @param request MultipartHttpServletRequest
     * @return FileInfo list in server
     */
    public static List<FileInfo> upload(HttpServletRequest request, Function<String, FileInfo> function) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        try {
            ServletContext servletContext = request.getSession().getServletContext();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
            // 如果是文件上传
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 获取所有上传的文件名
                Iterator<String> iterator = multiRequest.getFileNames();
                while (iterator.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iterator.next());
                    String md5 = SecureUtil.md5(file.getInputStream());
                    FileInfo fileInfo = function.apply(md5);
                    if(fileInfo == null) {
                        // 说明文件在服务器上还不存在，继续上传文件
                        String fileLocation = UploadUtils.write(file);
                        fileInfo = new FileInfo(file.getOriginalFilename(), md5, fileLocation);
                    } else {
                        // 说明文件在服务器上已经存在，只需要维护关系表即可，不需要重复上传
                        // 服务器上的源文件名称和正在上传的源文件名称虽然是同一个文件，但名字不一定相同，因此这里重新设置一下
                        if(!file.getOriginalFilename().equals(fileInfo.getOriginalName())) {
                            fileInfo.setOriginalName(file.getOriginalFilename());
                        }
                    }
                    fileInfoList.add(fileInfo);
                }
            } else {
                String message = "Can't found upload file! The request is not a MultipartHttpServletRequest.";
                throw new QuickBootClientException(message);
            }
        } catch (IOException e) {
            throw new QuickBootException(e);
        }
        return fileInfoList;
    }

    private static String write(MultipartFile file) throws IOException {
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        String fileType = StrUtil.isBlank(suffix) ? UNKNOWN_FILE_TYPE_DIR : suffix;
        String generatedFileName = TimestampIdUtils.timestampId() + Const.UNDERSCORE + File.separatorChar + file.getOriginalFilename();
        Path uploadFilePath = Paths.get(UPLOAD_ROOT_PATH + fileType + File.separatorChar + generatedFileName);
        Files.createDirectories(uploadFilePath.getParent());
        Files.copy(file.getInputStream(), Files.createFile(uploadFilePath), StandardCopyOption.REPLACE_EXISTING);
        return uploadFilePath.toAbsolutePath().toString();
    }

}
