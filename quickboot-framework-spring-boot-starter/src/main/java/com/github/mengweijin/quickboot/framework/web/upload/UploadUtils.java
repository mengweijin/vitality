package com.github.mengweijin.quickboot.framework.web.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quickboot.framework.util.TimestampIdUtils;
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

/**
 * @author mengweijin
 */
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
     * FileUploadChecker.getFileExistInServer(String md5) 返回 null 表示在服务器上找不到当前文件已经上传过的记录。
     * 因此，这个方法表示忽略文件是否已经上传的检查。即，一律上传，不管是否曾经上传过。
     * @param request HttpServletRequest
     * @return List of FileInfo
     */
    public static List<FileInfo> upload(HttpServletRequest request) {
        return upload(request, md5 -> null);
    }

    /**
     * 上传文件。根据文件后缀名自动分文件夹存放。
     *
     * @param request MultipartHttpServletRequest
     * @return FileInfo list in server
     */
    public static List<FileInfo> upload(HttpServletRequest request, FileUploadChecker fileUploadChecker) {
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
                    FileInfo fileInfo = fileUploadChecker.getFileExistInServer(md5);
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