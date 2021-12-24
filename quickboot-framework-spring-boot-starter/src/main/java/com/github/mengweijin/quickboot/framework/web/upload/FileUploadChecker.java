package com.github.mengweijin.quickboot.framework.web.upload;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@FunctionalInterface
public interface FileUploadChecker {

    /**
     * 函数式接口
     * 默认不做检查，返回 null。如果子类要重写这个方法，可以根据 MD5 在数据库中检查是否文件已经存在。
     * String md5 = SecureUtil.md5(file.getInputStream());
     * String md5Hex = DigestUtil.md5Hex(file.getBytes());
     * 注意：MD5 操作是一件比较耗费性能的计算，尤其是文件较大时。
     *
     * @param md5 md5
     * @return 返回在服务器上的文件的绝对全路径。 如果返回 null, 表示服务器不存在当前文件。否则表示已存在。
     */
    FileInfo getFileExistInServer(String md5);
}
