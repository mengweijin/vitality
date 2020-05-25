package com.github.mengweijin.quickboot.framework.util;

import com.github.mengweijin.quickboot.framework.constant.Const;
import com.github.mengweijin.quickboot.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author mengweijin
 */
@Slf4j
public class DownLoadUtils {

    public static void download(File file, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("multipart/form-data");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + setDownloadHeader(request, file.getName()));
            FileUtils.copyFile(file, response.getOutputStream());
        } catch (ClientAbortException e) {
            //捕获此异常表示用户停止下载
            log.warn("User cancel download.");
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public static String setDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String encodeFileName = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
            encodeFileName = encodeFileName.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            encodeFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (agent.contains("Chrome")) {
            // google浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
            encodeFileName = encodeFileName.replace("+", " ");
        } else {
            // 其它浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
        }
        return encodeFileName;

    }

    /**
     * 文件支持分块下载和断点续传
     *
     * @param file     文件
     * @param request  请求
     * @param response 响应
     */
    public static void chunkDownload(File file, HttpServletRequest request, HttpServletResponse response) {
        String range = request.getHeader(HttpHeaders.RANGE);
        log.debug("current request rang:" + range);
        //开始下载位置
        long startByte = 0;
        //结束下载位置
        long endByte = file.length() - 1;
        log.debug("File start location：{}，File end location：{}，File length：{}", startByte, endByte, file.length());

        //有range的话
        if (range != null && range.contains("bytes=") && range.contains(Const.DASH)) {
            range = range.substring(range.lastIndexOf(Const.EQUALS) + 1).trim();
            String[] ranges = range.split(Const.DASH);
            try {
                //判断range的类型, 如文件长度为10字节
                if (ranges.length == 1) {
                    //类型一：bytes=-5
                    if (range.startsWith(Const.DASH)) {
                        endByte = Long.parseLong(ranges[0]);
                    }
                    //类型二：bytes=5-
                    else if (range.endsWith(Const.DASH)) {
                        startByte = Long.parseLong(ranges[0]);
                    }
                }
                //类型三：bytes=3-8
                else if (ranges.length == 2) {
                    startByte = Long.parseLong(ranges[0]);
                    endByte = Long.parseLong(ranges[1]);
                }

            } catch (NumberFormatException e) {
                startByte = 0;
                endByte = file.length() - 1;
                log.error("Range Occur Error,Message:{}",e.getLocalizedMessage());
            }
        }

        //要下载的长度
        long contentLength = endByte - startByte + 1;
        //文件名
        String fileName = file.getName();
        //文件类型
        String contentType = request.getServletContext().getMimeType(fileName);

        //解决下载文件时文件名乱码问题
        byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
        fileName = new String(fileNameBytes, 0, fileNameBytes.length, StandardCharsets.ISO_8859_1);

        //各种响应头设置
        //支持断点续传，获取部分字节内容：
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        //http状态码要为206：表示获取部分内容
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        //inline表示浏览器直接使用，attachment表示下载，fileName表示下载的文件名
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + fileName);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
        // Content-Range，格式为：[要下载的开始位置]-[结束位置]/[文件总大小]
        response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + startByte + "-" + endByte + "/" + file.length());

        //已传送数据大小
        long transmitted = 0;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {

            byte[] buff = new byte[4096];
            int len = 0;
            randomAccessFile.seek(startByte);
            //判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！
            //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
            while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            //处理不足buff.length部分
            if (transmitted < contentLength) {
                len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                outputStream.write(buff, 0, len);
                transmitted += len;
            }

            outputStream.flush();
            response.flushBuffer();
            log.debug("Download completed：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (ClientAbortException e) {
            //捕获此异常表示用户停止下载
            log.warn("User stop download：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (IOException e) {
            log.error("User download IO Exception，Message：{}", e.getLocalizedMessage());
        }
    }

}
