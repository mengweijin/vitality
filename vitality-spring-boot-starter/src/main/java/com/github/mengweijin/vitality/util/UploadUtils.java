package com.github.mengweijin.vitality.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.ftp.Ftp;
import com.github.mengweijin.vitality.exception.BusinessException;
import com.github.mengweijin.vitality.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 文件上传。
 * @author Meng Wei Jin
 **/
@Slf4j
public class UploadUtils {

    /**
     * 上传文件存放根路径
     */
    public static final String UPLOAD_ROOT_PATH = Const.PROJECT_PATH + "upload" + File.separatorChar;

    /**
     * 上传到文件服务器（注释：保存记录到数据库） 这里仅给出一个示例，具体由使用者来参考并重写自己的业务实现。
     * @param destPath 如："/opt/upload"
     * @return 文件ID或文件路径，具体根据文件服务器的不同来决定
     */
    private static List<String> uploadToFTP(HttpServletRequest request, String destPath, String host, int port, String user, String password) {
        return upload(request, multipartFile -> {
            try(Ftp ftp = new Ftp(host, port, user, password)) {
                String newFileName = generateNewFileName(multipartFile.getOriginalFilename());
                ftp.upload(destPath, newFileName, multipartFile.getInputStream());
                // Here: Insert File info entity into database table if you need.
                // FileInfo fileInfo = new FileInfo(originalFilename, newFileName, fileSize, fileType, md5, location);
                // fileService.insert(fileInfo);
                return destPath + File.separatorChar + newFileName;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 上传到应用所在的文件系统
     * @param request request
     * @return 文件在磁盘中的绝对路径。
     */
    public static List<String> upload(HttpServletRequest request) {
        return upload(request, multipartFile -> {
            Path path = UploadUtils.write(multipartFile);
            return path.toAbsolutePath().toString();
        });
    }

    /**
     * 上传文件。根据文件后缀名自动分文件夹存放。
     *
     * @param request MultipartHttpServletRequest
     * @param function
     * @return FileInfo list in server
     */
    public static <R> List<R> upload(HttpServletRequest request, Function<MultipartFile, R> function) {
        List<R> fileList = new ArrayList<>();
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
                    R r = function.apply(file);
                    fileList.add(r);
                }
            } else {
                String message = "Can't found upload file! The request is not a MultipartHttpServletRequest.";
                throw new ClientException(message);
            }
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        return fileList;
    }

    public static Path write(MultipartFile file) {
        try(InputStream in = file.getInputStream()) {
            Path path = prepareUploadPath(file.getOriginalFilename());
            Files.copy(in, Files.createFile(path), StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path write(File file) {
        try {
            Path path = prepareUploadPath(file.getName());
            Files.copy(file.toPath(), Files.createFile(path), StandardCopyOption.REPLACE_EXISTING);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path prepareUploadPath(String fileName) throws IOException {
        String datePath = "" + DateUtil.thisYear() + File.separatorChar + DateUtil.thisMonth() + File.separatorChar + DateUtil.thisDayOfMonth();
        String uploadPath = UPLOAD_ROOT_PATH + datePath + File.separatorChar + generateNewFileName(fileName);

        Path path = Paths.get(uploadPath);
        Files.createDirectories(path.getParent());
        return path;
    }

    public static String generateNewFileName(String fileName) {
        return TimestampIdUtils.timestampId() + Const.UNDERSCORE + fileName;
    }
}
