package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.util.IdUtil;
import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.exception.ClientException;
import com.github.mengweijin.quickboot.framework.exception.ServerException;
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
public class WebUploadUtils {

    public static final String UPLOAD_PATH = Const.PROJECT_PATH + "upload" + File.separatorChar;

    /**
     * 上传文件
     *
     * @param request MultipartHttpServletRequest
     * @return file list in server
     */
    public static List<File> uploadFile(HttpServletRequest request) {
        List<File> uploadFileList = new ArrayList<>();
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

                    Path uploadFilePath = Paths.get(UPLOAD_PATH + IdUtil.fastUUID() + File.separatorChar + file.getOriginalFilename());
                    Files.createDirectories(uploadFilePath.getParent());
                    Files.copy(file.getInputStream(), Files.createFile(uploadFilePath), StandardCopyOption.REPLACE_EXISTING);
                    // add to return list
                    uploadFileList.add(uploadFilePath.toFile());
                }
            } else {
                String message = "Can't found upload file! The request is not a MultipartHttpServletRequest.";
                log.error(message);
                throw new ClientException(message);
            }
        } catch (IOException e) {
            throw new ServerException(e);
        }
        return uploadFileList;
    }
}
