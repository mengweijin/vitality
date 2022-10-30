package com.github.mengweijin.quickboot.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.exception.QuickBootException;
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

    public static void upload(HttpServletRequest request, Consumer<List<String>> consumer) {
        upload(null, request, consumer);
    }
    /**
     * 文件上传。@PostMapping("/upload")
     * 可以传入一个 Function<String, FileInfo> Lambda 表达式来检查文件是否已经在服务器上有一份了。
     *
     * @param request request
     * @param consumer 函数式接口。上传文件后，可以在数据库生成一条文件记录来保存当前文件所在的位置等信息。
     *                 可以在这个方法里面再调用一个异步方法来保存文件到第三方文件服务器中。
     */
    public static void upload(String moduleName, HttpServletRequest request, Consumer<List<String>> consumer) {
        List<String> pathList = UploadUtils.upload(moduleName, request);
        consumer.accept(pathList);
    }

    public static String uploadOne(HttpServletRequest request) {
        return uploadOne(null, request);
    }

    public static String uploadOne(String moduleName, HttpServletRequest request) {
        List<String> list = upload(moduleName, request);
        return CollUtil.isEmpty(list) ? null : list.get(0);
    }

    public static List<String> upload(HttpServletRequest request) {
        return upload(null, request);
    }

    /**
     * 上传文件。根据文件后缀名自动分文件夹存放。
     *
     * @param request MultipartHttpServletRequest
     * @return FileInfo list in server
     */
    public static List<String> upload(String moduleName, HttpServletRequest request) {
        List<String> pathList = new ArrayList<>();
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
                    Path path = UploadUtils.write(file, moduleName);
                    pathList.add(path.toAbsolutePath().toString());
                }
            } else {
                String message = "Can't found upload file! The request is not a MultipartHttpServletRequest.";
                throw new QuickBootClientException(message);
            }
        } catch (IOException e) {
            throw new QuickBootException(e);
        }
        return pathList;
    }

    public static Path write(MultipartFile file, String moduleDirName) throws IOException {
        String uploadPath = buildUploadPath(file.getOriginalFilename(), moduleDirName);
        Path path = Paths.get(uploadPath);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), Files.createFile(path), StandardCopyOption.REPLACE_EXISTING);
        return path;
    }

    public static Path write(File file, String moduleDirName) throws IOException {
        String uploadPath = buildUploadPath(file.getName(), moduleDirName);
        Path path = Paths.get(uploadPath);
        Files.createDirectories(path.getParent());
        Files.copy(file.toPath(), Files.createFile(path), StandardCopyOption.REPLACE_EXISTING);
        return path;
    }

    public static String buildUploadPath(String fileName, String moduleName) {
        moduleName = StrUtil.blankToDefault(moduleName, Const.EMPTY);
        String suffix = FileUtil.getSuffix(fileName);
        suffix = CharSequenceUtil.isBlank(suffix) ? UNKNOWN_FILE_TYPE_DIR : suffix;
        fileName = TimestampIdUtils.timestampId() + Const.UNDERSCORE + fileName;
        return UPLOAD_ROOT_PATH + moduleName + File.separatorChar + suffix + File.separatorChar + fileName;
    }

}
