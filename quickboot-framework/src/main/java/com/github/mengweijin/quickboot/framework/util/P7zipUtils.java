package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.system.OsInfo;
import com.github.mengweijin.quickboot.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 压缩/解压缩工具类，使用7z
 * linux需要安装p7zip
 * windows需存在7z.exe
 *
 * @author mengweijin
 */
@Slf4j
public class P7zipUtils {

	private final static String CMD_PREFIX_WINDOWS = Const.PROJECT_PATH + File.separator + "files/7z/7z.exe";

	private final static String CMD_PREFIX_LINUX = "7za";

	/**
	 * 压缩/解压缩命令前缀，根据操作系统类型决定
	 */
	private final static String CMD_PREFIX = new OsInfo().isWindows() ? CMD_PREFIX_WINDOWS : CMD_PREFIX_LINUX;

	public static void compress(List<String> filePathList, String destPath) {
		compress(filePathList, null, destPath);
	}

	/**
	 * 压缩
	 *
	 * @param filePathList 需要压缩在一起的所有文件的绝对路径的集合
	 * @param password     压缩密码
	 */
	public static void compress(List<String> filePathList, String password, String destPath) {
		try {
			String srcPath = String.join(" ", filePathList);

			File dir = FileUtil.file(destPath);
			if (dir.exists()) {
				FileUtils.forceDelete(dir);
			}

			String command;
			if (password == null) {
				command = String.format("%s a -r %s %s", CMD_PREFIX, destPath, srcPath);
			} else {
				command = String.format("%s a -p%s -r %s %s", CMD_PREFIX, password, destPath, srcPath);
			}

			RuntimeUtil.exec(command);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	public static String decompress(String filePath) {
		return decompress(filePath, null);
	}

	/**
	 * 解压
	 *
	 * @param filePath 压缩包绝对路径
	 * @param password 解压密码
	 * @return 解压到的目标路径
	 */
	public static String decompress(String filePath, String password) {
		try {
			Assert.notNull(filePath, "this argument is required; it must not be null!");
			File zipFile = new File(filePath);
			if (!zipFile.exists()) {
				throw new FileNotFoundException(filePath);
			}
			String zipFileName = zipFile.getName();
			zipFileName = zipFileName.substring(0, zipFileName.lastIndexOf(Const.DOT));
			String destPath = zipFile.getParent() + File.separator + zipFileName;

			File dir = new File(destPath);
			if (dir.exists()) {
				FileUtils.cleanDirectory(dir);
			} else {
				FileUtils.forceMkdir(dir);
			}

			String command;
			if (password == null) {
				command = String.format("%s x -aoa \"%s\" -o\"%s\"", CMD_PREFIX, zipFile.getAbsolutePath(), destPath);
			} else {
				command = String.format("%s x -p%s -aoa \"%s\" -o\"%s\"", CMD_PREFIX, password, zipFile.getAbsolutePath(), destPath);
			}

			RuntimeUtil.exec(command);
			return destPath;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
